package com.example.chatto.domain.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Entity Chat for Room Database
 * @param id the entity chat id
 * @param number the recipient's phone number
 * @param date the creation date of the chat
 * @param avatar the recipient's avatar
 */
@Entity(tableName = "chat")
data class DbChat(
    @PrimaryKey(true) val id: Int = 0,
    val number: String,
    val date: String,
    val avatar: Int
)
/**
 * Entity Message for Room Database
 * @param messageId the entity message id
 * @param chatId the chat id this message belongs to
 * @param date the creation date of the message
 * @param sender the owner of the message
 * @param text the text of the message
 */
@Entity(tableName = "message")
data class DbMessage(
    @PrimaryKey(true) val messageId: Int = 0,
    val chatId: Int,
    val date: String,
    val sender: String,
    val text: String
)

/**
 * Relation ChatWithMessages one-to-many for Room Database,
 * between a DbChat and its DbMessages
 */
data class ChatWithMessages(
    @Embedded val chat: DbChat,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatId"
    )
    val messages: List<DbMessage?>?
)
