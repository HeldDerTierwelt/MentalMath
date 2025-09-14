/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.helddertierwelt.mentalmath.data.dao.AdditionTaskDao
import com.helddertierwelt.mentalmath.data.dao.DivisionTaskDao
import com.helddertierwelt.mentalmath.data.dao.MultiplicationTaskDao
import com.helddertierwelt.mentalmath.data.dao.SubtractionTaskDao
import com.helddertierwelt.mentalmath.data.entity.AdditionTask
import com.helddertierwelt.mentalmath.data.entity.DivisionTask
import com.helddertierwelt.mentalmath.data.entity.MultiplicationTask
import com.helddertierwelt.mentalmath.data.entity.SubtractionTask

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