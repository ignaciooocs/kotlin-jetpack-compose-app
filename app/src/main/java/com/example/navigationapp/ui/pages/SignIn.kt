package com.example.navigationapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navigationapp.ui.components.toast
import com.example.navigationapp.ui.theme.MyBlue
import com.example.navigationapp.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignIn(
    navController: NavController,
    userState: UserViewModel = viewModel(),
    onNavigateToSignUp: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(userState.session.value) {
        if (userState.session.value) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    var inputEmail: String by remember { mutableStateOf("") }
    var inputPassword: String by remember { mutableStateOf("") }

    fun validateFields(email: String, password: String): Boolean {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast("Por favor no deje los campos vacíos", context)
            return false
        }
        return true
    }

    fun signIn(email: String, password: String) {
        if (!validateFields(email, password)) { return }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser

                    user?.getIdToken(true)?.addOnSuccessListener { result ->
                        if (result != null) {
                            val token = result.token
                            userState.updateUserSession(
                                user.displayName ?: "Nombre no encontrado",
                                user.email ?: "Email no encontrado",
                                token ?: "Token no encontrado"
                            )
                            userState.updateSession(true)
                        } else {
                            toast("No token found", context)
                            userState.updateSession(true)
                        }
                    }

                    toast("El usuario ${user?.email} inició sesión exitosamente", context)
                    navController.navigate("home")
                } else {
                    // Handle sign-in failure
                    toast("Inicio de sesión fallido", context)
                }
            }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(1f).padding(vertical = 20.dp)
        ) {
            TextField(
                label = { Text("Email") },
                value = inputEmail,
                onValueChange = { value -> inputEmail = value },
            )
            TextField(
                label = { Text("Password") },
                value = inputPassword,
                onValueChange = { value -> inputPassword = value }
            )
        }
        Button(
            onClick = { signIn(inputEmail, inputPassword) }
        ) { Text("Iniciar Sesión") }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(1f)
        ){
            Text("¿No tienes una cuenta?")
            IconButton(
                modifier = Modifier.fillMaxWidth(1f),
                onClick = { onNavigateToSignUp() }
            ) { Text("Regístrate", color = MyBlue, textDecoration = TextDecoration.Underline) }
        }
    }
}