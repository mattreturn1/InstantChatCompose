package com.example.chatto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.repo.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * the loginViewModel annotated with @HiltViewModel manages the operation on DbChat objects
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val chatList = chatRepository.getAllChat()

    suspend fun addChat(chat: DbChat) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.insertChat(chat)
            }
        }
    }

    suspend fun removeChat(chat: DbChat) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.deleteChat(chat)
            }
        }
    }
}
