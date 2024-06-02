package com.example.chatto.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * a formatter function for chat and message date during composition
 * @param date a string representing the date in format dd.MM.yyyy HH.mm.ss
 * @return if the creation of the chat/message is today only the time (HH.mm.ss) else only the date (dd.MM.yyyy)
 */
@RequiresApi(Build.VERSION_CODES.O)
fun formatter(date: String): String {
    if (date.substring(0, 10) == LocalDateTime.now().toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ) {
        return date.substring(11)
    }
    return date.substring(0, 10)
}

/**
 * a generic class to map a string in a Flow
 * @param provider a lambda function to map a String object and a Flow<T>
 */
class GenericDataMap<T>(private val provider: (String) -> Flow<T>) {
    private val flowMap = mutableMapOf<String, Flow<T>>()
    operator fun get(id: String) = flowMap.getOrPut(id) { provider(id) }
}