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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chatto.R
import com.example.chatto.domain.vo.DbChat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatDialogView(
    chatList: List<DbChat>,
    onDismiss: () -> Unit,
    onAdd: (DbChat) -> Unit
) {
    var number by rememberSaveable { mutableStateOf("") }

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
                    value = number,
                    onValueChange = { input ->
                        number = input
                    },
                    prefix = { Text("+39") },
                    modifier = Modifier.padding(8.dp),
                    label = { Text("Cell-Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    visualTransformation = VisualTransformation.None,
                    isError = !isValidText(number)
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

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        onClick = {
                            onDismiss()
                            onAdd(
                                DbChat(
                                    0, number, date,
                                    when (Random.nextInt(0, 3)) {
                                        0 -> R.drawable._0491849
                                        1 -> R.drawable._0491830
                                        else -> {
                                            R.drawable._0496275
                                        }
                                    },
                                )
                            )
                        },
                        enabled = isValidText(number) && chatList.none { dbChat -> dbChat.number == number }
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

fun isValidText(number: String): Boolean {
    return (number.length == 10) && number.isNotEmpty() && number.matches(Regex("[0-9]+")) && number != "0000000000"
}
