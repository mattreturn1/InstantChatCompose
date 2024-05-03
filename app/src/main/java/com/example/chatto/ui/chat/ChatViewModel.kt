package com.example.chatto.ui.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatto.domain.vo.DbMessage
import com.example.chatto.repo.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val isInSelectionMode = mutableStateOf(false)
    fun updateSelectionMode(value: Boolean) {
        isInSelectionMode.value = value
    }

    val messageList = GenericDataMap { id ->
        chatRepository.getChatWithMessages(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())
    }

    suspend fun addMessage(message: DbMessage) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.insertMessage(message)
            }
        }
    }

    suspend fun deleteMessage(message: DbMessage) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.deleteMessage(message)
            }
        }
    }
}

class GenericDataMap<T>(private val provider: (String) -> Flow<T>) {
    private val flowMap = mutableMapOf<String, Flow<T>>()
    operator fun get(id: String) = flowMap.getOrPut(id) { provider(id) }
}
