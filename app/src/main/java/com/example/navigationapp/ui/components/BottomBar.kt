package com.example.navigationapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navigationapp.ui.theme.MyBlue
import com.example.navigationapp.viewmodel.UserViewModel

class Buttons(val label: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun BottomBar (navController: NavController) {
    BottomAppBar(
        containerColor = MyBlue,
        contentColor = Color.White,
        modifier = Modifier.clip(RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)).height(90.dp),
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().background(MyBlue),
            ) { ListIconsBar(navController) }
        },
    )
}

@Composable
fun ListIconsBar (navController: NavController) {
    val buttons = listOf(
        Buttons(label = "Home", icon = Icons.Filled.Home, onClick = { navController.navigate("home") }),
        Buttons(label = "profile", icon = Icons.Filled.AccountCircle, onClick = { navController.navigate("profile") }),
    )
    buttons.forEach {
        IconButton(onClick = { it.onClick() }, modifier = Modifier.width(80.dp)) { Icon(imageVector = it.icon, contentDescription = it.label) }
    }
}
