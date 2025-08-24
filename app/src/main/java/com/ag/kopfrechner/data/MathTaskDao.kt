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

    @Query("SELECT * FROM math_tasks WHERE difficultyMultiply = :difficulty")
    suspend fun getMultiplyTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultyPlus = :difficulty")
    suspend fun getPlusTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultyMinus = :difficulty")
    suspend fun getMinusTasksByDifficulty(difficulty: Int): List<MathTask>

    @Query("SELECT * FROM math_tasks WHERE difficultyDivide = :difficulty")
    suspend fun getDivideTasksByDifficulty(difficulty: Int): List<MathTask>
}