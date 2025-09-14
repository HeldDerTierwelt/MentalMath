/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ag.kopfrechner.data.entity.AdditionTask

@Dao
interface AdditionTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: AdditionTask)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<AdditionTask>)

    @Query("SELECT * FROM tasks_addition WHERE difficultyAddition = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getAdditionTasksByDifficulty(difficulty: Int): List<AdditionTask>

    @Query("SELECT COUNT(*) FROM tasks_addition")
    suspend fun getCount(): Int
}

