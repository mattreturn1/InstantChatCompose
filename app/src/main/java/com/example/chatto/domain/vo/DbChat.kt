package com.example.chatto.domain.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "chat")
data class DbChat(
    @PrimaryKey val number: String,
    val date: String?,
)

@Entity(tableName = "message")
data class DbMessage(
    @PrimaryKey(true) val id: Int = 0,
    val date: String?,
    val owner: String,
    val text: String?,
)

data class ChatWithMessages(
    @Embedded val chat: DbChat,
    @Relation(
        parentColumn = "number",
        entityColumn = "owner"
    )
    val messages: List<DbMessage?>?
)
