package com.example.myapplication.profile.ui.state

sealed interface ProfileState {
    data object Loading : ProfileState
    data class ProfileData(
        val userName: String,
        val userId: String,
        val totalBalance: Long,
        val creditCardsBalance: Long,
        val depositsBalance: Long,
        val loansBalance: Long,
        val features: Int,
        val isDarkTheme: Boolean,
        val isLangEnglish: Boolean,
        val isNotificationsEnabled: Boolean,
        val isFaceIdEnabled: Boolean
    ) : ProfileState
}
