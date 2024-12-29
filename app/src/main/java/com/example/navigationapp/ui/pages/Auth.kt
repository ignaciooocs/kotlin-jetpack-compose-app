package com.example.navigationapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.navigationapp.ui.theme.MyBlue
import com.example.navigationapp.viewmodel.UserViewModel

@Composable
fun Auth(
    navController: NavController,
    innerPadding: PaddingValues,
    userViewModel: UserViewModel
) {
    val selectedTabIndex = remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar Sesión", "Registrarse")

    Column(modifier = Modifier.padding(innerPadding)) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            contentColor = Color.White,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                    color = MyBlue
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex.value) {
            0 -> SignIn(navController, userViewModel, onNavigateToSignUp = { selectedTabIndex.value = 1 })
            1 -> SignUp(navController, onNavigateToSignIn = { selectedTabIndex.value = 0 })
        }
    }
}