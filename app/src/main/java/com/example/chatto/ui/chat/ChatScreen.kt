package com.example.chatto.ui.chat

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.chatto.domain.vo.DbNumber
import com.example.chatto.ui.chat.components.MessageDialogView
import com.example.chatto.ui.chat.components.MessageItemsList
import com.example.chatto.ui.login.LoginViewModel
import com.example.chatto.ui.utils.FakeMessage
import kotlinx.coroutines.launch

/**
 * this Screen contains the Chat user interface whit all messages belong to the selected chat,
 * which consist of a Scaffold with a TopAppBar with the chat recipient number and a back arrow
 * to return to HomeScreen, a floatingActionButton to insert a new message,
 * this button invokes a MessageDialogView to insert the text of the message and
 * a MessageList which contains all messages exchanged order by creation date (the most recent on bottom),
 * if the user long click on a message item it will be selected and it will appear on the TopAppBar
 * a SelectionTopAppBar
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    id: String,
    navController: NavHostController,
    chatViewModel: ChatViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()

) {

    val openAlertDialog = rememberSaveable { mutableStateOf(false) }

    val messagesListState by chatViewModel.messageList[id].collectAsState(initial = emptyList())

    /**
     * recipient's prefix and number
     */
    val prefix = messagesListState.firstOrNull()?.chat?.number?.prefix
    val number = messagesListState.firstOrNull()?.chat?.number?.number

    /**
     * a flag to coordinate the fake generator response
     */
    val sent = rememberSaveable { mutableStateOf(false) }

    /**
     * a flag to coordinate the selection mode
     */
    val isInSelectionMode = chatViewModel.isInSelectionMode.value
    val selectedItems = chatViewModel.selectedItems

    /**
     * user's profile
     */
    val profile by loginViewModel.profile.collectAsState(initial = emptyList())
    val myPrefix = profile.firstOrNull()?.number?.prefix
    val myNumber = profile.firstOrNull()?.number?.number

    /**
     * for handling presses of the system back button
     */
    BackHandler(
        enabled = isInSelectionMode,
    ) {
        chatViewModel.updateSelectionMode(false)
        selectedItems.clear()
    }

    /**
     * to change the selectionMode value in the case that there aren't selected messages
     */
    LaunchedEffect(
        key1 = isInSelectionMode,
        key2 = selectedItems.size,
    ) {
        if (isInSelectionMode && selectedItems.isEmpty()) {
            chatViewModel.updateSelectionMode(false)
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
                            text = "+$prefix $number",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack("home", false)
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Close")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    chatViewModel.updateSelectionMode(false)
                    selectedItems.clear()
                    openAlertDialog.value = true
                },

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
                    senderNumber = DbNumber(myPrefix, myNumber),
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
                FakeMessage(chatId = id, number = DbNumber(prefix, number)) {
                    coroutineScope.launch {
                        chatViewModel.addMessage(it)
                    }
                    sent.value = false
                }
            }
            MessageItemsList(
                myProfileNumber = DbNumber(myPrefix, myNumber),
                isInSelectionMode = isInSelectionMode,
                updateSelectionMode = { chatViewModel.updateSelectionMode(it) },
                selectedItems = selectedItems,
                list = messagesListState,
            )
        }
    }
}

/**
 * the selection top app bar appear only when there is at least one message selected, it shows:
 * - a cancel button to exit from selection mode,
 * - the number of messages to delete,
 * - the button to delete the selected messages
 */
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