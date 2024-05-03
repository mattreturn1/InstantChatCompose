package com.example.chatto.domain.db

import androidx.room.RoomDatabase
import com.example.chatto.domain.vo.DbChat
import com.example.chatto.domain.vo.DbMessage


@androidx.room.Database(
    entities = [DbChat::class, DbMessage::class], version = 1
)

abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}