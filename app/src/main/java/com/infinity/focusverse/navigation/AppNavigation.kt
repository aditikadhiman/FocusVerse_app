package com.infinity.focusverse.navigation

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.infinity.focusverse.uifiles.Screens.auth.*
import com.infinity.focusverse.uifiles.screen.AddItemScreen

@Composable
fun AppNavigation(
    googleLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,navController: NavHostController
){

    val auth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) Screen.HomeScreen.route else Screen.Welcome.route

//    val navController = rememberNavController();
    NavHost(navController = navController,startDestination =startDestination){
        composable(Screen.Welcome.route) { WelcomeScreen(navController,googleLauncher)}
        composable(Screen.Login.route) { LoginScreen(navController)}
        composable(Screen.SignUp.route) { SignUpScreen(navController = navController)}
        composable(Screen.HomeScreen.route) { HomeScreen(navController = navController)}
        composable(Screen.TermsAndConditions.route) {
            TermsAndConditionsScreen(onBack = { navController.popBackStack() })
        }

//        composable(Screen.Section.route){ SectionScreen(navController = navController)}
//        composable(Screen.Profile.route){ Profile(navController = navController)}
        composable(
            route = Screen.AddItem.route,
            arguments = listOf(
                navArgument("sectionId") { type = NavType.StringType },
                navArgument("subSectionId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getString("sectionId") ?: ""
            val subSectionId = backStackEntry.arguments?.getString("subSectionId")
            if (subSectionId != null) {
                AddItemScreen(sectionId = sectionId, subSectionId = subSectionId)
            }
        }
    }
}
