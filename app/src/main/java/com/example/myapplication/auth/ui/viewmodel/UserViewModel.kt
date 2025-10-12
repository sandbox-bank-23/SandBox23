package com.example.myapplication.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.auth.ui.state.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(value = UserState.IsUnauthorized)
    val userState = _userState.asStateFlow()

    fun authorize() {
        _userState.value = UserState.IsAuthorized
    }
}