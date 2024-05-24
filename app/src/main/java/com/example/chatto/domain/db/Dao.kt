package com.example.chatto.domain.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.chatto.domain.vo.ChatWithMessages
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.domain.vo.DbMessage
import com.example.chatto.domain.vo.DbProfile
import kotlinx.coroutines.flow.Flow

/**
 * Dao interface to query and update the Room database
 */
@androidx.room.Dao
interface Dao {

    /**
     * to insert a chat in the database, if the chat already exist the insert is ignored
     * @param chat the chat
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(chat: DbChat)

    /**
     * to delete a chat in the database
     * @param chat the chat
     *
     */
    @Delete
    suspend fun deleteChat(chat: DbChat)

    /**
     * to select all chats in the database
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
     * to insert a message in the database
     * @param message the message
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: DbMessage)

    /**
     * to delete a message in the database
     * @param message the message
     */
    @Delete
    suspend fun deleteMessage(message: DbMessage)


    /**
     * to insert a profile in the database
     * @param profile the user profile
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDbProfile(profile: DbProfile)

    /**
     * to delete a profile in the database
     * @param profile the user profile
     *
     */
    @Delete
    suspend fun deleteDbProfile(profile: DbProfile)

    /**
     * to select all profile in the database
     */
    @Query("SELECT * FROM profile")
    fun getDbProfile(): Flow<List<DbProfile>>


}