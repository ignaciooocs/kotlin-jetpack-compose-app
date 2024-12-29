package com.example.navigationapp.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.navigationapp.R
import com.example.navigationapp.ui.theme.MyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar (title: String, route: String?) {
    val context = LocalContext.current.applicationContext
    TopAppBar(
        title = { Text(title) },
        navigationIcon = { NavigationIcon(context) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MyBlue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun NavigationIcon (context: Context) {
    IconButton(
        onClick = { toast("Aplicaciones Móviles Iot ", context) }
    ) {
        Icon(
            painter = painterResource(id= R.drawable.mesage),
            contentDescription = "Iot"
        )
    }
}

val toast = { text: String, context: Context ->
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

