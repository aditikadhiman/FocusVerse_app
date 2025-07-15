package com.infinity.focusverse.viewModel

import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.common.util.concurrent.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.FirebaseFirestore
import com.infinity.focusverse.Validator
import com.infinity.focusverse.model.User
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.state.ui.SignUpUiEvent
import com.infinity.focusverse.state.ui.SignUpUiState
import com.infinity.focusverse.utils.FirebaseUtils
import com.infinity.focusverse.utils.FirebaseUtils.getUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ViewModel(){

    private val TAG = SignUpViewModel::class.simpleName

    var signUpUIState = mutableStateOf(SignUpUiState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    private val _navigateToHome = mutableStateOf(false)
    val navigateToHome: State<Boolean> = _navigateToHome

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: State<String?> = _toastMessage

    fun onEvent(event:SignUpUiEvent){
        when(event){
            is SignUpUiEvent.FirstNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    firstName = event.firstName
                )
                printState()
            }
            is SignUpUiEvent.LastNameChanged ->{
                signUpUIState.value = signUpUIState.value.copy(
                    lastName = event.lastName
                )
                printState()
            }
            is SignUpUiEvent.EmailChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    email = event.email
                )
                printState()
            }
            is SignUpUiEvent.PasswordChanged ->{
                signUpUIState.value = signUpUIState.value.copy(
                    password = event.password
                )
                printState()
            }
            is SignUpUiEvent.PrivacyPolicyCheckBoxClicked ->{
                signUpUIState.value = signUpUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
            is SignUpUiEvent.RegisterButtonClicked ->{
                signUp()
            }
            else-> {}
        }
        validateDataWithRules()
    }

    private fun printState(){
        Log.d(TAG,"Inside_printState")
        Log.d(TAG,signUpUIState.value.toString())
    }

    private fun validateDataWithRules(){
        val fNameResult = Validator.validateFirstName(
            fName = signUpUIState.value.firstName
        )
        val lNameResult = Validator.validateLast(
            lName = signUpUIState.value.lastName
        )
        val emailResult = Validator.validateEmail(
            email = signUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = signUpUIState.value.password
        )
        val privacyPolicyResult = Validator.validatePrivacyPolicyAccepted(
            isAccepted = signUpUIState.value.privacyPolicyAccepted
        )

        Log.d(TAG,"Inside_validateDataWithRules")
        Log.d(TAG,"fNameResult = $fNameResult")
        Log.d(TAG, "lNameResult = $lNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG,"privacyPolicyResult=$privacyPolicyResult")

        signUpUIState.value = signUpUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyAccepted = privacyPolicyResult.status
        )

        allValidationsPassed.value = fNameResult.status && lNameResult.status
                && emailResult.status && passwordResult.status && privacyPolicyResult.status
    }

    private fun signUp(){
        Log.d(TAG,"Inside_signUp")
        printState()

        createUserInFirebase(
            Email = signUpUIState.value.email,
            Password = signUpUIState.value.password
        )

    }

    private fun createUserInFirebase(Email : String,Password: String){
        signUpInProgress.value=true

        auth.createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener{
                signUpInProgress.value=false
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"is successful = ${it.isSuccessful}")

                if(it.isSuccessful){
                    saveUserInFirestore()
                }else{
                    _toastMessage.value = "Registration failed"
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception=${it.localizedMessage}")
            }
    }

    private fun saveUserInFirestore(){
        val userId = FirebaseUtils.getUserId() ?: return
        val user  =  User(
            uid = userId,
            firstName = signUpUIState.value.firstName,
            lastName = signUpUIState.value.lastName,
            email = signUpUIState.value.email,
            createdAt = FirebaseUtils.getCurrentTimestamp()
        )

        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                _toastMessage.value = "Registration successful"
                _navigateToHome.value = true
            }
            .addOnFailureListener {
                _toastMessage.value = "Failed to save user: ${it.localizedMessage}"
                Log.e(TAG,"Firestore save failed",it)
            }
    }

    fun logout(navController: NavController){
        auth.signOut()
        val authStateListener = AuthStateListener{
            //You're using it to navigate back to the Welcome screen only after Firebase confirms the user is signed out.
            if(it.currentUser==null){
                Log.d(TAG,"Inside sign out screen")
                navController.navigate(Screen.Welcome.route)
            }else{
                Log.d(TAG,"Inside sign out is not complete")
            }
        }

        auth.addAuthStateListener(authStateListener)
    }//You must remove the listener later to avoid memory leaks in production apps:
    //Otherwise, the listener may keep observing even after the screen is destroyed.
}