package com.infinity.focusverse.state.ui

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var emailError: Boolean =false,
    var passwordError: Boolean = false,

    // Forgot password dialog
//    var isResetDialogVisible: Boolean = false,
    var resetEmail: String = "",
    var resetEmailError: Boolean = false,

    // Feedback states
//    var isResetSuccess: Boolean = false,
//    var resetErrorMessage: String? = null
)