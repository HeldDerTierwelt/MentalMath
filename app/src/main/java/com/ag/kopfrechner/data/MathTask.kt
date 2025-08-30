package com.ag.kopfrechner.data

import androidx.room.Entity

@Entity(
    tableName = "math_tasks",
    primaryKeys = ["operand1", "operand2"]
)
data class MathTask(
    val operand1: Int,
    val operand2: Int,
    val difficultyAddition: Int,
    val difficultySubtraction: Int,
    val difficultyMultiplication: Int,
    val difficultyDivision: Int
)