package com.example.chatto.ui.chat

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatto.domain.vo.DbMessage
import com.example.chatto.ui.chat.components.MessageDialogView
import com.example.chatto.ui.chat.components.MessageItemsList
import com.example.chatto.ui.utils.FakeMessage
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    id: String,
    number: String,
    navController: NavHostController,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val messagesListState by chatViewModel.messageList[id].collectAsState(initial = emptyList())

    val sent = remember { mutableStateOf(false) }

    var isInSelectionMode = chatViewModel.isInSelectionMode.value
    val selectedItems = remember {
        mutableStateListOf<DbMessage>()
    }
    BackHandler(
        enabled = isInSelectionMode,
    ) {
        isInSelectionMode = false
        selectedItems.clear()
    }

    LaunchedEffect(
        key1 = isInSelectionMode,
        key2 = selectedItems.size,
    ) {
        if (isInSelectionMode && selectedItems.isEmpty()) {
            isInSelectionMode = false
        }
    }

    Scaffold(
        topBar =
        {
            if (isInSelectionMode) {
                SelectionModeTopAppBar(
                    chatViewModel = chatViewModel,
                    selectedItems = selectedItems,
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            style = MaterialTheme.typography.titleLarge.copy(
                                lineHeight = 28.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.W500
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = "+39 $number",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openAlertDialog.value = true },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (openAlertDialog.value) {
                val coroutineScope = rememberCoroutineScope()
                MessageDialogView(
                    chatId = id,
                    number = "0000000000",
                    onDismiss = {
                        openAlertDialog.value = false
                    },
                    onAdd = {
                        coroutineScope.launch {
                            chatViewModel.addMessage(it)
                        }
                        sent.value = true
                    }
                )
            }
            if (sent.value) {
                val coroutineScope = rememberCoroutineScope()
                FakeMessage(chatId = id, number = number) {
                    coroutineScope.launch {
                        chatViewModel.addMessage(it)
                    }
                    sent.value = false
                }
            }
            MessageItemsList(
                isInSelectionMode = isInSelectionMode,
                updateSelectionMode = { chatViewModel.updateSelectionMode(it) },
                selectedItems = selectedItems,
                list = messagesListState,
            )
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SelectionModeTopAppBar(
    selectedItems: SnapshotStateList<DbMessage>,
    chatViewModel: ChatViewModel

) {
    TopAppBar(
        title = {
            Text(
                text = "Messages to delete: ${selectedItems.size}",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    chatViewModel.updateSelectionMode(false)
                    selectedItems.clear()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier,
            ) {
                val coroutineScope = rememberCoroutineScope()
                IconButton(
                    onClick = {
                        selectedItems.forEach { dbMessage ->
                            coroutineScope.launch {
                                chatViewModel.deleteMessage(
                                    dbMessage
                                )
                            }
                        }
                        chatViewModel.updateSelectionMode(false)
                        selectedItems.clear()

                    },
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}