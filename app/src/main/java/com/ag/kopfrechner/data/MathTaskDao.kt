package com.ag.kopfrechner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MathTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: MathTask)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<MathTask>)

    @Query("SELECT * FROM math_tasks WHERE difficultyAddition = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getAdditionTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultySubtraction = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getSubtractionTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultyMultiplication = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getMultiplicationTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultyDivision = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getDivisionTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT COUNT(*) FROM math_tasks")
    suspend fun getCount(): Int
}