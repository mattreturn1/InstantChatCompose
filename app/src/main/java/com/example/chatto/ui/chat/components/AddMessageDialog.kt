package com.example.chatto.ui.chat.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chatto.domain.vo.DbMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageDialogView(
    chatId: String,
    number: String,
    onDismiss: () -> Unit,
    onAdd: (DbMessage) -> Unit

) {
    var text by rememberSaveable { mutableStateOf("") }
    val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Text(
                    text = "Write message",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.padding(8.dp),
                    isError = text.isEmpty()
                )
                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }

                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            onAdd(DbMessage(0, chatId.toInt(), date, number, text))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        enabled = text.isNotEmpty()
                    ) {
                        Text(text = "Send")
                    }
                }
            }
        }
    }
}