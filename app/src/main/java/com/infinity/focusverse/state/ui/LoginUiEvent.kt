package com.infinity.focusverse.state.ui

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()

    data class PassWordChanged(val password: String) : LoginUiEvent()

    object LoginButtonClicked: LoginUiEvent()

    data class ResetEmailChanged(val resetEmail: String) : LoginUiEvent()
    object SendResetLink : LoginUiEvent()
}