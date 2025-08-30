package com.ag.kopfrechner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MathTask::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mathTaskDao(): MathTaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "math_task_database"
                            ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}