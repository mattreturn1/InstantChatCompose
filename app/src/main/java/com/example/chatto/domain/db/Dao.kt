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


@androidx.room.Dao
interface Dao {

    //DAO Chat
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: DbChat)

    @Delete
    suspend fun deleteChat(chat: DbChat)

    @Query("SELECT * FROM chat")
    fun getAllChat(): Flow<List<DbChat>>

    //DAO Message
    @Transaction
    @Query("SELECT * FROM chat WHERE number=:numberId")
    fun getChatWithMessages(numberId: String): Flow<List<ChatWithMessages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: DbMessage)

    @Delete
    suspend fun deleteMessage(message: DbMessage)


}