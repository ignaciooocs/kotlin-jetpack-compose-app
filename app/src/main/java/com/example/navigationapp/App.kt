package com.example.navigationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationapp.ui.components.BottomBar
import com.example.navigationapp.ui.components.ToolBar
import com.example.navigationapp.ui.pages.Add
import com.example.navigationapp.ui.pages.Auth
import com.example.navigationapp.ui.pages.Home
import com.example.navigationapp.ui.pages.Profile
import com.example.navigationapp.ui.theme.MyBlue
import com.example.navigationapp.viewmodel.UserViewModel

@Composable
fun App () {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val currentRoute: String? = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        topBar = {
            when (currentRoute) {
                "home" -> ToolBar("Inicio", currentRoute)
                "profile" -> ToolBar("Perfil", currentRoute)
                "add" -> ToolBar("Agregar dispositivo", currentRoute)
            }
                 },
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (userViewModel.session.value) { BottomBar(navController) } },
        floatingActionButton = {
            if (currentRoute == "home") {
                IconButton(onClick = { navController.navigate("add") }) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier.height(40.dp).width(40.dp).background(MyBlue))
                }
            }
        }
    ) { innerPadding ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { Home(navController, innerPadding, userViewModel) }
                composable("profile") { Profile(navController, innerPadding, userViewModel) }
                composable("add") { Add(navController, innerPadding) }
                composable("auth") { Auth(navController, innerPadding, userViewModel) }
            }
    }
}


