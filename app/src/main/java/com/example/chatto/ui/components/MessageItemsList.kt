package com.example.chatto.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatto.domain.vo.ChatWithMessages
import com.example.chatto.domain.vo.DbMessage


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageItemsList(
    isInSelectionMode: Boolean,
    selectedItems: SnapshotStateList<DbMessage>,
    modifier: Modifier = Modifier,
    list: List<ChatWithMessages>,
    updateSelectionMode: (Boolean) -> Unit,
) {

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        val listMessages = list.firstOrNull()

        if (listMessages != null) {
            items(
                items = listMessages.messages.orEmpty()
            ) { message ->
                val isSelected = selectedItems.contains(message)

                if (message != null) {
                    if(message.sender=="0000000000"){
                        MessageItem(
                            modifier = Modifier.width(370.dp).combinedClickable(
                                onClick = {
                                    if (isInSelectionMode) {
                                        if (isSelected) {
                                            selectedItems.remove(message)
                                        } else {
                                            selectedItems.add(message)
                                        }
                                    }
                                },
                                onLongClick = {
                                    if (isInSelectionMode) {
                                        if (isSelected) {
                                            selectedItems.remove(message)
                                        } else {
                                            selectedItems.add(message)
                                        }
                                    } else {
                                        updateSelectionMode(true)
                                        selectedItems.add(message)
                                    }
                                },
                            ),
                            messageText = message.text,
                            messageDate = message.date,
                            isSelected = isSelected,
                            isMine = true
                        )
                    }else{
                        MessageItem(
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    if (isInSelectionMode) {
                                        if (isSelected) {
                                            selectedItems.remove(message)
                                        } else {
                                            selectedItems.add(message)
                                        }
                                    }
                                },
                                onLongClick = {
                                    if (isInSelectionMode) {
                                        if (isSelected) {
                                            selectedItems.remove(message)
                                        } else {
                                            selectedItems.add(message)
                                        }
                                    } else {
                                        updateSelectionMode(true)
                                        selectedItems.add(message)
                                    }
                                },
                            ),
                            messageText = message.text,
                            messageDate = message.date,
                            isSelected = isSelected,
                            isMine = false
                        )
                    }

                }
            }
        }
    }
}