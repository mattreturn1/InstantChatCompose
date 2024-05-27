package com.example.chatto.ui.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chatto.R
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.domain.vo.DbNumber
import com.example.chatto.domain.vo.ValidPrefix
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

/**
 * a dialog to insert a new chat in ChatItemsList, a Card composed by two OutlinedTextField
 * to insert the prefix and the number of the recipient and two buttons, one to Cancel the operation and
 * the other to Add the chat
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatDialogView(
    chatList: List<DbChat>,
    myProfileNumber: DbNumber,
    onDismiss: () -> Unit,
    onAdd: (DbChat) -> Unit
) {
    var number by rememberSaveable { mutableStateOf("") }
    var prefix by rememberSaveable { mutableStateOf("") }

    val date: String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))

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
                    text = "Add chat",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                OutlinedTextField(
                    value = prefix,
                    onValueChange = { input ->
                        prefix = input
                    },
                    leadingIcon = { Text(text = "+") },
                    modifier = Modifier.padding(8.dp),
                    label = { Text("Prefix") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = !ValidPrefix.contains(prefix)

                )
                OutlinedTextField(
                    value = number,
                    onValueChange = { input ->
                        number = input
                    },
                    modifier = Modifier.padding(8.dp),
                    label = { Text("Cell-Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = !isValidText(number) || number == myProfileNumber.number
                )
                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Cancel")
                    }

                    /**
                     * when the user add a new chat it's created a new object DbChat, with a random avatar
                     * the button is enabled only if the recipient's complete number
                     * is different from user's complete number
                     * and prefix and number are valid and the complete number is not already added
                     * in the chat list
                     */
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        onClick = {
                            onDismiss()
                            onAdd(
                                DbChat(
                                    0, DbNumber(prefix, number), date,
                                    when (Random.nextInt(0, 3)) {
                                        0 -> R.drawable.avatar3
                                        1 -> R.drawable.avatar1
                                        else -> {
                                            R.drawable.avatar2
                                        }
                                    },
                                )
                            )
                        },
                        enabled = if ((prefix == myProfileNumber.prefix) && (number == myProfileNumber.number)) {
                            false
                        } else {
                            (prefix != "") && (ValidPrefix.contains(prefix) && isValidText(number)
                                    && chatList.none { dbChat ->
                                (dbChat.number.number == number && dbChat.number.prefix == prefix)
                            })
                        }
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

/**
 * in this function there is a check of the number without prefix
 */
fun isValidText(number: String): Boolean {
    return (number.length == 10) && number.isNotEmpty() && number.matches(Regex("[0-9]+"))
}
