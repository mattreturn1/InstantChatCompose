package com.example.chatto

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatto.ui.dispatcher.Dispatcher
import com.example.chatto.ui.chat.ChatScreen
import com.example.chatto.ui.home.HomeScreen
import com.example.chatto.ui.login.LoginScreen
import com.example.chatto.ui.theme.ChattoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * in class MainActivity there is a NavController which controls the navigation between app's activities
 * in particular it declares a navigation graph composed by:
 * - a dispatcher route, to decide if show the login screen (if this is the user's first access
 *   to the app) or not,
 * - a login route
 * - a home route
 * - a chat route with argument id to identify the correct chat and arguments prefix and number,
 *   which must have passed to Chat activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChattoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "dispatcher"
                    ) {
                        composable(route = "home") {
                            HomeScreen(navController = navController)
                        }
                        composable(route = "chat/{id}/{prefix}/{number}") { backStackEntry ->
                            val chatId = backStackEntry.arguments?.getString("id")
                                ?: throw IllegalStateException("missing id from arguments")
                            val prefix = backStackEntry.arguments?.getString("prefix")
                                ?: throw IllegalStateException("missing id from arguments")
                            val number = backStackEntry.arguments?.getString("number")
                                ?: throw IllegalStateException("missing id from arguments")
                            ChatScreen(
                                navController = navController,
                                id = chatId,
                                prefix = prefix,
                                number = number
                            )
                        }
                        composable(route = "login") {
                            LoginScreen(navController = navController)
                        }
                        composable(route = "dispatcher") {
                            Dispatcher(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

