package com.example.chatto.ui.dispatcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

/**
 * this function observes the state from viewModel, if the user have already inserted his valid phone
 * number, then the navigation controller will navigate to HomeScreen else it will navigate to LoginScreen
 */
@Composable
fun Dispatcher(
    dispatcherViewModel: DispatcherViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by dispatcherViewModel.state.collectAsState()
    LaunchedEffect(state) {
        when (state) {
            LoginState.LOADING -> {

            }

            LoginState.LOGGED -> {
                navController.navigate("home") { popUpTo(0) }
            }

            LoginState.NOTLOGGED -> {
                navController.navigate("login") { popUpTo(0) }
            }
        }
    }
}