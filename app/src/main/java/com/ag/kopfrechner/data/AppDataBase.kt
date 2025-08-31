package com.ag.kopfrechner.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MathTask::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mathTaskDao(): MathTaskDao
}