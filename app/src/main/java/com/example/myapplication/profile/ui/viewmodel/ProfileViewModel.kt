package com.example.myapplication.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.interactor.ClearAppDataUseCase
import com.example.myapplication.profile.domain.interactor.GetUserDataUseCase
import com.example.myapplication.profile.domain.interactor.ThemeInteractor
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase
import com.example.myapplication.profile.ui.state.Features
import com.example.myapplication.profile.ui.state.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Suppress("MagicNumber", "UnderscoresInNumericLiterals", "unused")
class ProfileViewModel(
    private val updatesUseCase: UpdatesUseCase,
    val themeInteractor: ThemeInteractor,
    private val clearAppDataUseCase: ClearAppDataUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(
        ProfileState.Loading
    )
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _isLatestVersion = MutableStateFlow(0)
    val isLatestVersion: StateFlow<Int> = _isLatestVersion.asStateFlow()

    fun requestProfileData() {
        viewModelScope.launch {
            val data = getUserDataUseCase()
            _profileState.value = ProfileState.ProfileData(
                userName = data.name,
                userId = data.id,
                totalBalance = data.totalBalance,
                creditCardsBalance = data.creditCardsBalance,
                depositsBalance = data.depositsBalance,
                loansBalance = data.loansBalance,
                features = Features.THEME.flag + Features.FACE_ID.flag,
                isDarkTheme = getCurrentTheme(),
                isLangEnglish = false,
                isNotificationsEnabled = true,
                isFaceIdEnabled = false
            )
        }

    }

    fun changeTheme() {
        viewModelScope.launch {
            val currentTheme = themeInteractor.getTheme().first()

            themeInteractor.changeTheme()

            _profileState.value = when (val state = _profileState.value) {
                is ProfileState.ProfileData -> {
                    state.copy(isDarkTheme = !currentTheme)
                }

                else -> state
            }
        }
    }


    fun requestAppUpdate() {
        viewModelScope.launch {
            updatesUseCase.isLatestVersion().collect { response ->
                _isLatestVersion.value = if (Json.decodeFromString<Boolean>(response.response!!)) 1 else -1
            }
        }
    }

    fun dismissDialog() {
        _isLatestVersion.value = 0
    }

    fun requestLogOut() {
        viewModelScope.launch {
            clearAppDataUseCase()
        }
    }

    private fun formatUserId(userId: Long): String {
        val userIdString = userId.toString()
        return when (userIdString.length == 16) {
            true -> userIdString.replace(Regex("(\\d{4})(\\d{4})(\\d{4})(\\d{4})"), "$1 $2 $3 $4")
            false -> userIdString
        }
    }
    private fun getCurrentTheme(): Boolean {
        var isDarkTheme = false
        viewModelScope.launch {
            isDarkTheme = themeInteractor.getTheme().first()
        }
        return isDarkTheme
    }
}