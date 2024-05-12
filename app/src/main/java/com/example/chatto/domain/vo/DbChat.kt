package com.example.chatto.domain.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "chat")
data class DbChat(
    @PrimaryKey(true) val id: Int = 0,
    val number: String,
    val date: String?,
    val avatar: Int,
)

@Entity(tableName = "message")
data class DbMessage(
    @PrimaryKey(true) val messageId: Int = 0,
    val chatId: Int,
    val date: String?,
    val sender: String,
    val text: String,
)

data class ChatWithMessages(
    @Embedded val chat: DbChat,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatId"
    )
    val messages: List<DbMessage?>?
)
