package com.example.chatto.repo

import com.example.chatto.domain.db.Dao
import com.example.chatto.domain.vo.ChatWithMessages
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.domain.vo.DbMessage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * a repository interface to manage DbChat and DbMessage entities
 */
interface ChatRepository {

    fun getAllChat(): Flow<List<DbChat>>

    suspend fun insertChat(dbChat: DbChat)

    suspend fun deleteChat(dbChat: DbChat)


    suspend fun insertMessage(dbMessage: DbMessage)

    suspend fun deleteMessage(dbMessage: DbMessage)

    fun getChatWithMessages(id: String): Flow<List<ChatWithMessages>>
}

/**
 * this class will provide a @Singleton implementation of a ChatRepository
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class TasksModule {
    @Binds
    @Singleton
    abstract fun provideTaskRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}
/**
 * a repository implementation to manage DbChat and DbMessage entities
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val dao: Dao
) : ChatRepository {

    override fun getAllChat(): Flow<List<DbChat>> = dao.getAllChat()
    override suspend fun deleteChat(dbChat: DbChat) = dao.deleteChat(dbChat)
    override suspend fun insertChat(dbChat: DbChat) = dao.insertChat(dbChat)

    override fun getChatWithMessages(id: String): Flow<List<ChatWithMessages>> =
        dao.getChatWithMessages(id)
    override suspend fun deleteMessage(dbMessage: DbMessage) = dao.deleteMessage(dbMessage)
    override suspend fun insertMessage(dbMessage: DbMessage) = dao.insertMessage(dbMessage)

}

