package com.dluong.chatappclone.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dluong.chatappclone.feauture.auth.sign_in.SignInScreen
import com.dluong.chatappclone.feauture.auth.sign_up.SignUpScreen
import com.dluong.chatappclone.feauture.chat.ChatScreen
import com.dluong.chatappclone.feauture.home.HomeScreen

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(navController) {
        AppState(navController)
    }
}

fun NavGraphBuilder.setUpNavGraph(appState: AppState, navController: NavHostController) {
    composable(Screen.SignIn.route) {
        SignInScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(Screen.SignUp.route) {
        SignUpScreen(
            popUp = { appState.popUp() },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screen.Home.route) {
        HomeScreen(navController)
    }

    composable("chat/{channelId}&{channelName}", arguments = listOf(
        navArgument("channelId") {
            type = NavType.StringType
        },
        navArgument("channelName") {
            type = NavType.StringType
        }
    )) {
        val channelId = it.arguments?.getString("channelId") ?: ""
        val channelName = it.arguments?.getString("channelName") ?: ""
        ChatScreen(navController, channelId, channelName)
    }
}