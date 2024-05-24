package com.example.chatto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.chatto.ui.utils.PreferencesManager

@Composable
fun DispatcherScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData("LOGGED", "")) }
    if (data.value == "user-logged") {
        navController.navigate("home")
    } else {
        navController.navigate("login")
    }
}