package com.example.chatto.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatto.domain.vo.DbProfile
import com.example.chatto.repo.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * the loginViewModel annotated with @HiltViewModel manages the operation on DbProfile object
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    val profile = profileRepository.getDbProfile()

    suspend fun addProfile(profile: DbProfile) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                profileRepository.insertDbProfile(profile)
            }
        }
    }

    suspend fun removeProfile(profile: DbProfile) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                profileRepository.deleteDbProfile(profile)
            }
        }
    }
}
