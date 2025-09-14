/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.data.entity

import androidx.room.Entity

@Entity(
    tableName = "tasks_division",
    primaryKeys = ["operand1", "operand2"]
)
data class DivisionTask(
    val operand1: Int,
    val operand2: Int,
    val difficultyDivision: Int
)