package com.example.chatto.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatto.domain.vo.DbMessage
import com.example.chatto.repo.ChatRepository
import com.example.chatto.ui.utils.GenericDataMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * the loginViewModel annotated with @HiltViewModel manages the operation on DbChat entities
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    /**
     * to contain the selected items in selection mode
     */
    val selectedItems = mutableStateListOf<DbMessage>()

    /**
     * a state of the selection mode
     */
    val isInSelectionMode = mutableStateOf(false)
    fun updateSelectionMode(value: Boolean) {
        isInSelectionMode.value = value
    }

    /**
     * to get the chat with its all messages specified by its id
     */
    private val _messageList = GenericDataMap { id ->
        chatRepository.getChatWithMessages(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())
    }
    val messageList = _messageList

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


