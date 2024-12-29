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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigationapp.ui.components.toast
import com.example.navigationapp.ui.theme.MyBlue
import com.example.navigationapp.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(
    navController: NavController,
    onNavigateToSignIn: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance()

    var inputEmail: String by remember { mutableStateOf("") }
    var inputPassword: String by remember { mutableStateOf("") }

    fun validateFields(email: String, password: String): Boolean {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast("Por favor no deje los campos vacíos", context)
            return false
        }
        return true
    }

    fun registerUser (email: String, password: String) {
        if (!validateFields(email, password)) { return }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    toast("El usuario ${user?.email} se registró exitosamente", context)
                    navController.navigate("auth")
                } else {
                    toast("Usuario no registrado", context)
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
            onClick = { registerUser(inputEmail, inputPassword) }
        ) { Text("registrarse") }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            Text("¿Ya tienes una cuenta?")
            IconButton(
                modifier = Modifier.fillMaxWidth(1f),
                onClick = { onNavigateToSignIn() }
            ) { Text("Iniciar Sesión", color = MyBlue, textDecoration = TextDecoration.Underline) }
        }

    }
}