package com.example.navigationapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navigationapp.ui.components.toast
import com.example.navigationapp.viewmodel.FetchViewModel
import com.example.navigationapp.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Home (
    navController: NavController,
    innerPadding: PaddingValues,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current.applicationContext
    val viewModel: FetchViewModel = viewModel()
    val dataList by viewModel.dataList.collectAsState()

    var newname by remember { mutableStateOf("") }

    LaunchedEffect(userViewModel.session.value) {
        if (!userViewModel.session.value) {
            navController.navigate("auth")
        }
    }

    fun updateDevice(deviceId: String, newName: String, onResult: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("devices").document(deviceId)
            .update("name", newName)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun handleUpdateDevice(deviceId: String, newName: String) {
        updateDevice(deviceId, newName) { success ->
            if (success) {
                toast("Dispositivo actualizado con éxito", context)
                navController.navigate("home")
            } else { toast("Error al actualizar el dispositivo", context) }
        }
    }

    fun deleteDevice(deviceId: String, onResult: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("devices").document(deviceId)
            .delete()
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun handleDeleteDevice(deviceId: String) {
        deleteDevice(deviceId) { success ->
            if (success) {
                toast("Dispositivo eliminado con éxito", context)
                navController.navigate("home")
            } else { toast("Error al eliminar el dispositivo", context) }
        }
    }

    val userSession by userViewModel.userSession

    Column(modifier = Modifier.padding(innerPadding).fillMaxSize())  {
        Text("Bienvenido a nuestra applición ${userSession?.email}")
        LazyColumn {
            items(dataList) { dataItem ->
                Card(modifier = Modifier.padding(8.dp)) {
                    Row {
                        Text(modifier = Modifier.padding(8.dp), text = dataItem.name)
                        Button(onClick = {handleDeleteDevice(dataItem.id ?: "")}) {
                            Text("Eliminar")
                        }
                        Button(onClick = { handleUpdateDevice(dataItem.id ?: "", newname)}) {
                            Text("Actualizar")
                        }
                    }

                }
            }
        }
        TextField(
            value = newname,
            label = { Text("Nuevo nombre") },
            onValueChange = { value -> newname = value}
        )
    }
}

