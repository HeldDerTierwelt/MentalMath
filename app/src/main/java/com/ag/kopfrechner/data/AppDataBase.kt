/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ag.kopfrechner.data.dao.AdditionTaskDao
import com.ag.kopfrechner.data.dao.DivisionTaskDao
import com.ag.kopfrechner.data.dao.MultiplicationTaskDao
import com.ag.kopfrechner.data.dao.SubtractionTaskDao
import com.ag.kopfrechner.data.entity.AdditionTask
import com.ag.kopfrechner.data.entity.DivisionTask
import com.ag.kopfrechner.data.entity.MultiplicationTask
import com.ag.kopfrechner.data.entity.SubtractionTask

@Database(
    entities = [
        AdditionTask::class,
        SubtractionTask::class,
        MultiplicationTask::class,
        DivisionTask::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun additionTaskDao(): AdditionTaskDao
    abstract fun subtractionTaskDao(): SubtractionTaskDao
    abstract fun multiplicationTaskDao(): MultiplicationTaskDao
    abstract fun divisionTaskDao(): DivisionTaskDao
}