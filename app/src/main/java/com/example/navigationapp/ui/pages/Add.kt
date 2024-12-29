package com.example.navigationapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import com.example.navigationapp.ui.components.toast
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Add (
    navController: NavController,
    innerPadding: PaddingValues
) {
    val context = LocalContext.current.applicationContext
    var nameState by remember { mutableStateOf("") }
    data class Device(val name: String = "", val id: String? = null)

    fun addDevice (name: String, onResult: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val device = Device(name = name, id = "123456789")

        db.collection("devices")
            .add(device)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun handleAddDevice(name: String) {
        addDevice(name) { success ->
            if (success) {
                toast("Dispositivo agregado con éxito", context)
                navController.navigate("home")
            } else { toast("Error al agregar el dispositivo", context) }
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(innerPadding).fillMaxSize())  {
        TextField(
            label = { Text("Nombre del dispositivo") },
            value = nameState,
            onValueChange = { value -> nameState = value }
        )
        Button(onClick = { handleAddDevice(nameState) }) { Text("Agregar dispositivo") }
    }
}