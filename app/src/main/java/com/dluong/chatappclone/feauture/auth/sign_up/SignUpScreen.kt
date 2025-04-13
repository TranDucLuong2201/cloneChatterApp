package com.dluong.chatappclone.feauture.auth.sign_up

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dluong.chatappclone.R
import com.dluong.chatappclone.feauture.auth.UiState

@Composable
fun SignUpScreen(
    popUp: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val password = viewModel.password.collectAsState()
    val email = viewModel.email.collectAsState()
    val name = viewModel.name.collectAsState()
    val confirm = viewModel.confirm.collectAsState()

    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.value) {

        when (uiState.value) {
            is UiState.Success -> {
                viewModel.onSignUpSuccess(openAndPopUp)
            }

            is UiState.Error -> {
                Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo), contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email") },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Name") },
                placeholder = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Password") },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = confirm.value,
                onValueChange = { viewModel.updateConfirm(it) },
                label = { Text("Confirm Password") },
                placeholder = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = password.value.isNotEmpty() && confirm.value.isNotEmpty() && password.value != confirm.value
            )
            Spacer(modifier = Modifier.height(40.dp))
            if (uiState.value == UiState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {viewModel.signUp(name.value,email.value,password.value)},
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary
                    ),
                    enabled = name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && confirm.value.isNotEmpty() && password.value == confirm.value
                ) {
                    Text("Sign In", color = MaterialTheme.colorScheme.onPrimary)
                }
                TextButton(
                    onClick = { viewModel.onPopUp(popUp) },
                ) { Text("Already have an account? Sign In") }
            }
        }
    }
}

