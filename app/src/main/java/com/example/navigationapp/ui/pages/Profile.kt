package com.example.navigationapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.navigationapp.ui.components.toast
import com.example.navigationapp.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile (
    navController: NavController,
    innerPadding: PaddingValues,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance()

    fun logout () {
        auth.signOut()
        if (auth.currentUser == null) {
            userViewModel.clearUserSession()
            userViewModel.updateSession(false)
            navController.navigate("auth")
            toast("Sesión cerrada", context)
        } else {
            toast("Falló al cerrar sesión", context)
        }
    }

    Column (
        modifier = Modifier
            .padding(innerPadding)
    ) {
        Text("Profile")
        Text("Correo electronico: ${userViewModel.userSession.value?.email}")
        Button(onClick = { logout() }) { Text("Cerrar sesión") }
    }
}