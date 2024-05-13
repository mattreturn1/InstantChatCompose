package com.example.chatto.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatter(date: String): String {
    if (date.substring(0, 10) == LocalDateTime.now().toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ) {
        return date.substring(11)
    }
    return date.substring(0, 10)
}