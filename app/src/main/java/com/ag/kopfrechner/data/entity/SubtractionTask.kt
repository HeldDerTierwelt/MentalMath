package com.ag.kopfrechner.data.entity

import androidx.room.Entity

@Entity(
    tableName = "tasks_subtraction",
    primaryKeys = ["operand1", "operand2"]
)
data class SubtractionTask(
    val operand1: Int,
    val operand2: Int,
    val difficultySubtraction: Int
)