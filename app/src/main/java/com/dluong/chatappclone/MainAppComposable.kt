package com.dluong.chatappclone

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dluong.chatappclone.navigate.Screen
import com.dluong.chatappclone.navigate.rememberAppState
import com.dluong.chatappclone.navigate.setUpNavGraph
import com.dluong.chatappclone.ui.theme.ChatAppCloneTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {
    ChatAppCloneTheme {
        Surface {
            val appState = rememberAppState()
            val currentUser = FirebaseAuth.getInstance().currentUser
            val navController = rememberNavController()
            val hasUser = if (currentUser != null) Screen.Home.route else Screen.SignIn.route
            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = hasUser,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    setUpNavGraph(appState, navController = navController)
                }
            }
        }
    }
}