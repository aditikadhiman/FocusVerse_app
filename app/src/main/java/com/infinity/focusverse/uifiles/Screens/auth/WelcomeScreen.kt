package com.infinity.focusverse.uifiles.Screens.auth

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.infinity.focusverse.R
import com.infinity.focusverse.navigation.Screen
import com.infinity.focusverse.ui.theme.background
import com.infinity.focusverse.uifiles.Components.ButtonComponent
import com.infinity.focusverse.uifiles.Components.ButtonTransparentComponent
import com.infinity.focusverse.uifiles.Components.ContinueWithGoogleButton
import com.infinity.focusverse.uifiles.Components.DividerTextComponent
import com.infinity.focusverse.uifiles.Components.HeadingTextComponent


@Composable
fun WelcomeScreen(
    navController: NavController,
    googleLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val WebClientID = stringResource(id = R.string.WEB_CLIENT_ID)
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background), contentAlignment = Alignment.TopStart
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(149.dp))
                HeadingTextComponent(value = stringResource(id = R.string.Welcome))
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.log_in),
                    onButtonClicked = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = false }
                        }})
                        Spacer(modifier = Modifier.height(20.dp))
                        ButtonTransparentComponent(value = stringResource(id = R.string.sign_up),
                            onButtonClicked = {
                                navController.navigate(Screen.SignUp.route){
                                    popUpTo(Screen.Welcome.route){inclusive = false}
                                }
                            })
                        Spacer(modifier = Modifier.height(20.dp))
                        DividerTextComponent()
                        Spacer(modifier = Modifier.height(20.dp))
                        ContinueWithGoogleButton(value = stringResource(id = R.string.continue_with_google),
                            painter = painterResource(id = R.drawable.google_icon),
                            onButtonClicked = {
                                val gso =
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)  //google sign in option object
                                        .requestIdToken(WebClientID)
                                        .requestEmail()
                                        .build()
//                        val signInClient = GoogleSignIn.getClient(context,gso)
//                        val signInIntent = signInClient.signInIntent
//                        googleLauncher.launch(signInIntent)
                                val client = GoogleSignIn.getClient(context, gso)
                                googleLauncher.launch(client.signInIntent)
                            })


                    }
            }

        }
    }

