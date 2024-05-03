package com.example.chatto.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatto.domain.vo.DbChat


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItemsList(
    modifier: Modifier = Modifier,
    list: List<DbChat>,
    onOpenChat: (DbChat) -> Unit,
    onCloseChat: (DbChat) -> Unit,
) {
    LazyColumn(modifier = modifier, reverseLayout = true, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(
            items = list
        ) { chat ->
            ChatItem(
                chatNumber = chat.number,
                chatDate = chat.date,
                onOpenChat = {
                    onOpenChat(chat)
                },
                onClose = {
                    onCloseChat(chat)
                }
            )
        }
    }
}