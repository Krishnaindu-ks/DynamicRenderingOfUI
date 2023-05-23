package com.ks.dynamicrenderingui

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [QuestionResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionResponseDao(): QuestionResponseDao
}