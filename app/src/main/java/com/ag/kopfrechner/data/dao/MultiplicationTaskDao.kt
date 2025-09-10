package com.ag.kopfrechner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ag.kopfrechner.data.entity.MultiplicationTask

@Dao
interface MultiplicationTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: MultiplicationTask)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<MultiplicationTask>)

    @Query("SELECT * FROM tasks_multiplication WHERE difficultyMultiplication = :difficulty ORDER BY RANDOM() LIMIT 1")
    suspend fun getMultiplicationTasksByDifficulty(difficulty: Int): List<MultiplicationTask>

    @Query("SELECT COUNT(*) FROM tasks_multiplication")
    suspend fun getCount(): Int
}

