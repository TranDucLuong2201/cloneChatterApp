package com.dluong.chatappclone.feauture.auth.sign_in

import com.dluong.chatappclone.feauture.auth.AppViewModel
import com.dluong.chatappclone.feauture.auth.UiState
import com.dluong.chatappclone.navigate.Screen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(

) : AppViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onNavigate(openScreen: (String) -> Unit) {
        openScreen(Screen.SignUp.route)
    }

    fun onSignInSuccess(openScreen: (String) -> Unit) {
        openScreen(Screen.Home.route)
    }

    fun signIn(email: String, password: String) {
        _state.value = UiState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        _state.value = UiState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = UiState.Error("Sign In Error")

                } else {
                    _state.value = UiState.Error("Sign In error")
                }
            }
    }
}