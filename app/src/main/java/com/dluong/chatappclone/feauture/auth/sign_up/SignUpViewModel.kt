package com.dluong.chatappclone.feauture.auth.sign_up

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
class SignUpViewModel @Inject constructor() : AppViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirm = MutableStateFlow("")
    val confirm: StateFlow<String> = _confirm.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        _state.value = UiState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        it.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        ).addOnCompleteListener {
                            _state.value = UiState.Success
                        }
                        return@addOnCompleteListener
                    }
                    _state.value = UiState.Error("Sign Up error")

                } else {
                    _state.value = UiState.Error("Sign up error")
                }
            }
    }

    fun onSignUpSuccess(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Screen.Home.route, Screen.SignUp.route)
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirm(newConfirm: String) {
        _confirm.value = newConfirm
    }

    fun onPopUp(popUp: () -> Unit) {
        popUp()
    }

}