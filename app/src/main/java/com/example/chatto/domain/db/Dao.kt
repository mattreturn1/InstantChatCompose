package com.example.chatto.domain.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.chatto.domain.vo.ChatWithMessages
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.domain.vo.DbMessage
import kotlinx.coroutines.flow.Flow

/**
 * Dao interface to query and update the Room database
 */
@androidx.room.Dao
interface Dao {

    /**
     * to insert a @param chat in the database, if the chat already exist the insert is ignored
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(chat: DbChat)

    /**
     * to delete a @param chat in the database
     */
    @Delete
    suspend fun deleteChat(chat: DbChat)

    /**
     * to select all chat in the database
     */
    @Query("SELECT * FROM chat")
    fun getAllChat(): Flow<List<DbChat>>

    /**
     * to select all messages in the database associated with a chat
     */
    @Transaction
    @Query("SELECT * FROM chat WHERE id=:numberId")
    fun getChatWithMessages(numberId: String): Flow<List<ChatWithMessages>>

    /**
     * to insert a @param message in the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: DbMessage)
    /**
     * to delete a @param message in the database
     */
    @Delete
    suspend fun deleteMessage(message: DbMessage)


}