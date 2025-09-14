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
import com.helddertierwelt.mentalmath.data.entity.DivisionTask

@Dao
interface DivisionTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: DivisionTask)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<DivisionTask>)

    @Query("SELECT * FROM tasks_division WHERE difficultyDivision = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getDivisionTasksByDifficulty(difficulty: Int): List<DivisionTask>

    @Query("SELECT COUNT(*) FROM tasks_division")
    suspend fun getCount(): Int
}

