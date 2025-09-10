package com.ag.kopfrechner.data.entity

import androidx.room.Entity

@Entity(
    tableName = "tasks_addition",
    primaryKeys = ["operand1", "operand2"]
)
data class AdditionTask(
    val operand1: Int,
    val operand2: Int,
    val difficultyAddition: Int
)