package com.ag.kopfrechner.data.entity

import androidx.room.Entity

@Entity(
    tableName = "tasks_multiplication",
    primaryKeys = ["operand1", "operand2"]
)
data class MultiplicationTask(
    val operand1: Int,
    val operand2: Int,
    val difficultyMultiplication: Int
)