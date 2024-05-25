package com.example.chatto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.chatto.ui.utils.PreferencesManager

/**
 * this function observes the value from preferences file, in this case if the user have already inserted
 * his valid phone number, if in the file is present the string user-logged the navController goes to home
 * activity otherwise the navController goes to login activity
 */
@Composable
fun Dispatcher(
    navController: NavHostController
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData("LOGGED", "")) }
    if (data.value == "user-logged") {
        navController.navigate("home"){ popUpTo(0) }
    } else {
        navController.navigate("login")
    }
}