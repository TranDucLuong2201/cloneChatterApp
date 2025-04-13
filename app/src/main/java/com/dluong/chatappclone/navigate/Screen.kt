package com.dluong.chatappclone.navigate

sealed class Screen(val route: String) {

    data object SignIn : Screen("SignIn")
    data object SignUp : Screen("SignUp")
    data object Home : Screen("Home")
}

