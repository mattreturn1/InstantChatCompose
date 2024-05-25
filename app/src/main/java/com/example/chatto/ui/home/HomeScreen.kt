package com.example.chatto.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatto.domain.vo.DbNumber
import com.example.chatto.ui.home.components.ChatDialogView
import com.example.chatto.ui.home.components.ChatItemsList
import com.example.chatto.ui.login.LoginViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val chatListState by homeViewModel.chatList.collectAsState(emptyList())
    val openAlertDialog = rememberSaveable { mutableStateOf(false) }

    val profile by loginViewModel.profile.collectAsState(initial = emptyList())
    val myPrefix = profile.firstOrNull()?.number?.prefix
    val myNumber = profile.firstOrNull()?.number?.number
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Chatto",
                            fontWeight = FontWeight.W500,
                            fontStyle = FontStyle.Normal,
                            lineHeight = 24.sp,
                            fontSize = 28.sp
                        )
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Me: +$myPrefix $myNumber",
                            fontWeight = FontWeight.W500,
                            fontStyle = FontStyle.Normal,
                            lineHeight = 13.sp,
                            fontSize = 15.sp
                        )
                    }
                }
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
                    myProfileNumber = DbNumber(myPrefix, myNumber),
                    onDismiss = {
                        openAlertDialog.value = false
                    },
                    onAdd = {
                        coroutineScope.launch {
                            homeViewModel.addChat(it)
                        }
                    }
                )
            }
            val coroutineScope = rememberCoroutineScope()
            ChatItemsList(
                list = chatListState.reversed(),
                onOpenChat = { chat -> navController.navigate("chat/${chat.id}/${chat.number.prefix}/${chat.number.number}") },
                onCloseChat = { coroutineScope.launch { homeViewModel.removeChat(it) } }
            )
        }
    }
}

