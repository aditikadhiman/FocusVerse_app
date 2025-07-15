package com.infinity.focusverse.uifiles.Screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import com.infinity.focusverse.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.state.ui.SignUpUiEvent
import com.infinity.focusverse.ui.theme.TextColor
import com.infinity.focusverse.ui.theme.background
import com.infinity.focusverse.ui.theme.dialog1
import com.infinity.focusverse.uifiles.Components.Button2Component
import com.infinity.focusverse.uifiles.Components.ButtonComponent
import com.infinity.focusverse.uifiles.Components.ButtonTransparentComponent
import com.infinity.focusverse.uifiles.Components.CheckBoxComponent
import com.infinity.focusverse.uifiles.Components.ClickableLoginTextComponent
import com.infinity.focusverse.uifiles.Components.ContinueWithGoogleButton
import com.infinity.focusverse.uifiles.Components.HeadingTextComponent
import com.infinity.focusverse.uifiles.Components.DividerTextComponent
import com.infinity.focusverse.uifiles.Components.MyTextFieldComponent
import com.infinity.focusverse.uifiles.Components.PasswordTextFieldComposable
import com.infinity.focusverse.uifiles.Components.UnderlinedTextComponent
import com.infinity.focusverse.viewModel.SignUpViewModel


@Composable
fun SignUpScreen( navController: NavController) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val context = LocalContext.current
    val navigateToHome = signUpViewModel.navigateToHome.value
    val toastMessage = signUpViewModel.toastMessage.value

    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.SignUp.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = background
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = background
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(60.dp))
                HeadingTextComponent(value = stringResource(id = R.string.sign_up))
                Spacer(modifier = Modifier.height(30.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.FirstNameChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUIState.value.firstNameError
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.LastNameChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUIState.value.lastNameError
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.EmailChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUIState.value.emailError
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUIState.value.passwordError)
                Spacer(modifier = Modifier.height(20.dp))
                CheckBoxComponent(
                    value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {
                        navController.navigate(Screen.TermsAndConditions.route)
                    },
                    onCheckedChange = {
                        signUpViewModel.onEvent(
                            SignUpUiEvent.PrivacyPolicyCheckBoxClicked(it)
                        )
                    })
                Spacer(modifier = Modifier.height(30.dp))
                Button2Component(
                    value = stringResource(id = R.string.sign_up), onButtonClicked = {
                        signUpViewModel.onEvent(SignUpUiEvent.RegisterButtonClicked)
                    },
                    isEnabled = signUpViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(30.dp))
                ClickableLoginTextComponent(true, onTextSelected = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) {
                            inclusive = true
                        }
                    }
                })

            }
        }
        if (signUpViewModel.signUpInProgress.value) {
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

// Why do we use both _something and something?
//This is a common MVVM pattern:
//
//Property	Type	Access
//_navigateToHome	MutableState<Boolean>	Private (only ViewModel can update it)
//navigateToHome	State<Boolean>	Public (Composable can read it)
//
//This ensures UI can read the state but cannot modify it directly.

