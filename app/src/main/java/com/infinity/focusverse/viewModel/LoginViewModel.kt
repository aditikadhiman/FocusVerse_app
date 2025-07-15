package com.infinity.focusverse.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.infinity.focusverse.Validator
import com.infinity.focusverse.model.User
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.state.ui.LoginUiEvent
import com.infinity.focusverse.state.ui.LoginUiState
import com.infinity.focusverse.utils.FirebaseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel()  {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUiState())
    var allValidationPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    var resetEmailValidationPassed = mutableStateOf(false)

    private val _navigateToHome = mutableStateOf(false)
    val navigateToHome: State<Boolean> = _navigateToHome

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: State<String?> = _toastMessage


    fun onEvent(event: LoginUiEvent) {
        //LocalContext.current is a Compose-only function.
        // It can’t be used inside a ViewModel because ViewModels don't have access to the Compose tree or Composable scope.

        validateLoginUIDataWithRules()
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUiEvent.PassWordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUiEvent.LoginButtonClicked -> {
                login()
            }

            is LoginUiEvent.ResetEmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    resetEmail = event.resetEmail
                )
            }

            is LoginUiEvent.SendResetLink -> {
                sendResetPasswordEmail()
            }

        }
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )
        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        val resetEmailResult = Validator.validateResetEmail(
            resetEmail = loginUIState.value.resetEmail
        )
        loginUIState.value = loginUIState.value.copy(
            resetEmailError = resetEmailResult.status
        )

        resetEmailValidationPassed.value = resetEmailResult.status
        allValidationPassed.value = emailResult.status && passwordResult.status
    }

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                loginInProgress.value = false
                Log.d(TAG, "Inside_login_success")
                Log.d(TAG, "${it.isSuccessful}")

                if (it.isSuccessful) {
                    _toastMessage.value="Login Successful"
                    _navigateToHome.value = true
                } else {
                    _toastMessage.value = "Failed to Login"
                }
            }
            .addOnFailureListener {
                loginInProgress.value = false
                _toastMessage.value = "Login failed: ${it.localizedMessage}"
            }

    }

    fun sendResetPasswordEmail() {

        val resetEmail = loginUIState.value.resetEmail
        auth.sendPasswordResetEmail(resetEmail)
            .addOnCompleteListener { task ->
                _toastMessage.value = if (task.isSuccessful)
                    "Reset link sent to $resetEmail"
                else
                    "Error: ${task.exception?.localizedMessage}"
            }
    }

    var isLoggedIn = mutableStateOf(auth.currentUser != null)
        private set

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LoginViewModel", "Google sign-in success")
                    isLoggedIn.value = true

                    val user = auth.currentUser
                    val uid = user?.uid?: return@addOnCompleteListener
                    val displayName = user.displayName ?: ""
                    val nameParts = displayName.split(" ")
                    val firstName = nameParts.getOrNull(0) ?: ""
                    val lastName = nameParts.getOrNull(1) ?: ""
                    val email = user.email ?: ""

                    val newUser = User(
                        uid = uid,
                        firstName = firstName,
                        lastName = lastName,
                        email= email,
                        createdAt = FirebaseUtils.getCurrentTimestamp()
                    )

                    FirebaseUtils.getUsersCollection()
                        .document(uid)
                        .set(newUser)
                        .addOnSuccessListener {
                            _toastMessage.value = "Login successful with Google"
                            Log.d("LoginViewModel","User saved in Firestore")
                        }
                        .addOnFailureListener{
                            _toastMessage.value = "Login success but failed to save user in Firestore"
                            Log.e("LoginViewModel","Error saving user in Firestore",it)
                        }
                } else {
                    Log.e("LoginViewModel", "Google sign-in failed", task.exception)
                    isLoggedIn.value = false
                    _toastMessage.value= "Google sign-in failed"
                }
            }
    }

    fun logout() {
        auth.signOut()
        isLoggedIn.value = false
    }

    fun resetNavigation() {
        _navigateToHome.value = false
    }

    fun resetToast() {
        _toastMessage.value = null
    }
}




