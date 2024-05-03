package com.example.chatto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatto.ui.activity.ActivityScreen
import com.example.chatto.ui.chat.ChatScreen
import com.example.chatto.ui.theme.ChattoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChattoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "start"
                    ) {
                        composable(route = "start") {
                            ActivityScreen(navController = navController)
                        }
                        composable(route = "chat/{id}") { backStackEntry ->
                            val chatId = backStackEntry.arguments?.getString("id")
                                ?: throw IllegalStateException("missing id from arguments")
                            ChatScreen(
                                navController = navController,
                                id = chatId
                            )
                        }
                    }
                }
            }
        }
    }
}

