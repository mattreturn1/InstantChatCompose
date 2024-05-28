package com.example.chatto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatto.repo.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
/**
 * the dispatcherViewModel annotated with @HiltViewModel manages the login state
 */
@HiltViewModel
class DispatcherViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val profile = profileRepository.getDbProfile()

    val state = profile.map { profile ->
        if (profile.isEmpty()) {
            LoginState.NOTLOGGED
        } else {
            LoginState.LOGGED
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LoginState.LOADING)

}

enum class LoginState {
    LOADING,
    LOGGED,
    NOTLOGGED
}