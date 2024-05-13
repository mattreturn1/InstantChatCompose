package com.example.chatto.domain.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * An object DbModule which provides two function:
 * database: instance of a @Singleton database
 * dao: for the use of Dao interface
 */
@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun database(
        @ApplicationContext context: Context

    ): Database =
        Room.databaseBuilder(
            context, Database::class.java,
            "chat_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun dao(database: Database): Dao = database.dao()
}

