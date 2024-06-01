package com.example.chatto.domain.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Data class to represent a telephone number
 * @param prefix
 * @param number
 */
data class DbNumber(
    val prefix: String?,
    val number: String?
)

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
    @Embedded val number: DbNumber,
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
    @Embedded val sender: DbNumber,
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

/**
 * Entity Profile to represent a user data
 * @param number user's phone number
 */
@Entity(tableName = "profile")
data class DbProfile(
    @PrimaryKey(true) val id: Int = 0,
    @Embedded val number: DbNumber
)