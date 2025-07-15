package com.infinity.focusverse.uifiles.Screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.focusverse.R
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.state.ui.LoginUiEvent
import com.infinity.focusverse.ui.theme.TextColor
import com.infinity.focusverse.ui.theme.background
import com.infinity.focusverse.ui.theme.dialogEvery
import com.infinity.focusverse.uifiles.Components.Button2Component
import com.infinity.focusverse.uifiles.Components.ClickableLoginTextComponent
import com.infinity.focusverse.uifiles.Components.DividerTextComponent
import com.infinity.focusverse.uifiles.Components.HeadingTextComponent
import com.infinity.focusverse.uifiles.Components.MyTextFieldComponent
import com.infinity.focusverse.uifiles.Components.PasswordTextFieldComposable
import com.infinity.focusverse.uifiles.Components.ResetPasswordDialog
import com.infinity.focusverse.uifiles.Components.UnderlinedTextComponent
import com.infinity.focusverse.viewModel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val toastMessage = loginViewModel.toastMessage.value
    val navigateToHome = loginViewModel.navigateToHome.value

    val context = LocalContext.current

    // 👇 Toast
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            loginViewModel.resetToast() // Optional to clear toast state
        }
    }

    // 👇 Navigation
    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
            loginViewModel.resetNavigation()
        }
    }

    // STEP 1: Track dialog visibility
    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background), contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(120.dp))
                HeadingTextComponent(value = stringResource(id = R.string.log_in))
                Spacer(Modifier.height(40.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUiEvent.EmailChanged(it))
                    }, errorStatus = loginViewModel.loginUIState.value.emailError
                )
                Spacer(Modifier.height(20.dp))
                PasswordTextFieldComposable(
                    labelValue = stringResource(id = R.string.password),
                    imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUiEvent.PassWordChanged(it))
                    }, errorStatus = loginViewModel.loginUIState.value.passwordError
                )
                Spacer(Modifier.height(30.dp))
                UnderlinedTextComponent(
                    value = stringResource(id = R.string.forgot_password),
                    onClick = { showDialog.value = true }) //step 2
                Spacer(modifier = Modifier.height(30.dp))
                Button2Component(
                    value = stringResource(id = R.string.log_in),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUiEvent.LoginButtonClicked)
                    }, isEnabled = loginViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    navController.navigate(Screen.SignUp.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                })
            }


            // STEP 3: Add the dialog below your UI
            ResetPasswordDialog(
                showDialog = showDialog.value,
                onTextSelected = {
                    loginViewModel.onEvent(LoginUiEvent.ResetEmailChanged(it))
                },
                onDismiss = { showDialog.value = false },
                onSendLink = {
                    loginViewModel.onEvent(LoginUiEvent.SendResetLink)
                }, isEnabled = loginViewModel.resetEmailValidationPassed.value,
                errorStatus = loginViewModel.loginUIState.value.resetEmailError
            )
        }
        if (loginViewModel.loginInProgress.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp), // 5.dp is too small to render visibly
                    strokeWidth = 2.dp,
                    color = TextColor
                )
            }
        }
    }
}

