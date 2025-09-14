/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helddertierwelt.mentalmath.data.entity.SubtractionTask

@Dao
interface SubtractionTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: SubtractionTask)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<SubtractionTask>)

    @Query("SELECT * FROM tasks_subtraction WHERE difficultySubtraction = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getSubtractionTasksByDifficulty(difficulty: Int): List<SubtractionTask>

    @Query("SELECT COUNT(*) FROM tasks_subtraction")
    suspend fun getCount(): Int
}

