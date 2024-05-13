package com.example.chatto.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.chatto.domain.vo.DbMessage
import com.example.chatto.domain.vo.FakeMessages
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FakeMessage(
    chatId: String,
    number: String,
    onAdd: (DbMessage) -> Unit
) {
    val text = FakeMessages.random()
    val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
    onAdd(DbMessage(0, chatId.toInt(), date, number, text))
}


