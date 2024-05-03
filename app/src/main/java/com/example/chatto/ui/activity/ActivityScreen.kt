package com.example.chatto.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatto.ui.components.ChatDialogView
import com.example.chatto.ui.components.ChatItemsList
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    activityViewModel: ActivityViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val chatListState by activityViewModel.chatList.collectAsState(emptyList())
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Hi There! You're using Chatto"
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openAlertDialog.value = true },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "AddChat")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (openAlertDialog.value) {
                val coroutineScope = rememberCoroutineScope()
                ChatDialogView(
                    chatList = chatListState,
                    onDismiss = {
                        openAlertDialog.value = false
                    },
                    onAdd = {
                        coroutineScope.launch {
                            activityViewModel.addChat(it)
                        }
                    }
                )
            }
            val coroutineScope = rememberCoroutineScope()
            ChatItemsList(
                list = chatListState,
                onOpenChat = { chat -> navController.navigate("chat/${chat.number}") },
                onCloseChat = { coroutineScope.launch { activityViewModel.removeChat(it) } }
            )
        }
    }
}

