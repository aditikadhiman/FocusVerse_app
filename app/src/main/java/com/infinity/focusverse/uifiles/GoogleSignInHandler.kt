package com.infinity.focusverse.uifiles

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.infinity.focusverse.navigation.AppNavigation
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.viewModel.LoginViewModel


@Composable
fun GoogleSignInHandler() {
    val navController = rememberNavController()
    val loginViewModel : LoginViewModel = hiltViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let {
                loginViewModel.signInWithGoogle(it)
            }
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "Google sign in failed", e)
        }
    }

    // Observe for navigation trigger
    LaunchedEffect(loginViewModel.isLoggedIn.value) {
        if (loginViewModel.isLoggedIn.value) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        }
    }

    AppNavigation(launcher,navController)
}
