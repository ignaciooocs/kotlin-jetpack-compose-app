package com.example.navigationapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    data class UserSession(
        val name: String,
        val email: String,
        val token: String
    )

    private val _userSession = mutableStateOf<UserSession?>(UserSession("","Not started",""))
    val userSession: State<UserSession?> = _userSession

    fun updateUserSession(name: String, email: String, token: String) {
        _userSession.value = UserSession(name, email, token)
    }

    fun clearUserSession() {
        _userSession.value = UserSession("", "logout", "")
    }

    private val _session = mutableStateOf<Boolean>(false)
    val session: State<Boolean> = _session

    fun updateSession (state: Boolean) {
        _session.value = state
    }
}