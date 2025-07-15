package com.infinity.focusverse.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object HomeScreen : Screen("home")
    object TermsAndConditions: Screen("terms_and_conditions")
    object Section: Screen("section")
    object AddItem: Screen("add_item_screen?sectionId={sectionId}&subSectionId={subSectionId}") {
        fun passArgs(sectionId: String, subSectionId: String? = null): String {
            return "add_item_screen?sectionId=$sectionId&subSectionId=${subSectionId ?: ""}"
        }
    }
    object Profile : Screen("profile")

}
