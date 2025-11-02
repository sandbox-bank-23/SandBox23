package com.example.myapplication.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase
import com.example.myapplication.profile.ui.state.Features
import com.example.myapplication.profile.ui.state.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.random.Random

@Suppress("MagicNumber", "UnderscoresInNumericLiterals", "unused")
class ProfileViewModel(
    private val updatesUseCase: UpdatesUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(
        ProfileState.Loading
    )
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _isLatestVersion = MutableStateFlow(0)
    val isLatestVersion: StateFlow<Int> = _isLatestVersion.asStateFlow()

    fun requestProfileData() {
        // Реализовать обращение к репозиторию для получения данных профиля и статистики
        // Пока передаются дефолтные значения
        val userId = Random.nextLong(4566_0000_5564_0005, 4566_0000_5564_9999)
        _profileState.value = ProfileState.ProfileData(
            userName = "Ivanova Oksana",
            userId = formatUserId(userId),
            totalBalance = 50_000,
            creditCardsBalance = 0,
            depositsBalance = 40_000,
            loansBalance = 8_770_000,
            features = Features.THEME.flag + Features.FACE_ID.flag,
            isDarkTheme = false,
            isLangEnglish = false,
            isNotificationsEnabled = true,
            isFaceIdEnabled = false
        )
    }

    fun switchTheme(isDarkTheme: Boolean) {
        // Реализовать изменение темы приложения
    }

    fun switchLang(isEnglishLang: Boolean) {
        // Можно реализовать изменение языка, но эту функцию не требуется реализовывать
    }

    fun switchNotifications(isNotificationsEnabled: Boolean) {
        // Можно реализовать разрешение уведомлений, но эту функцию не требуется реализовывать
    }

    fun switchFaceId(isFaceIdEnabled: Boolean) {
        // Можно реализовать активацию входа по FaceId, но эту функцию не требуется реализовывать
    }

    fun requestAppUpdate() {
        // Реализовать вызов процесса обновления приложения
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
        // Реализовать вызов процесса выхода из аккаунта
    }

    private fun formatUserId(userId: Long): String {
        val userIdString = userId.toString()
        return when (userIdString.length == 16) {
            true -> userIdString.replace(Regex("(\\d{4})(\\d{4})(\\d{4})(\\d{4})"), "$1 $2 $3 $4")
            false -> userIdString
        }
    }

}