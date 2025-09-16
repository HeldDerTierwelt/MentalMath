/*
Mental Math - Android app for practicing mental arithmetic
Copyright (C) 2025 HeldDerTierwelt

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see https://www.gnu.org/licenses/gpl-3.0.md.
*/

package com.helddertierwelt.mentalmath.presentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameState(
    val operand1: Int = 0,
    val operand2: Int = 0,
    val operator: Operation = Operation.ADDITION,
    val input: String = "",
    val tasks: List<TaskResult> = emptyList(),
    val enabledOperators: Map<Operation, Pair<Float, Float>> = mutableMapOf(),
    val isCorrect: Boolean? = null,

    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val skippedAnswers: Int = 0,
    val totalAnswers: Int = 0,

    val isGameStarted: Boolean = false,
    val isTimerRunning: Boolean = false,
    val activeTime: Long = 0,
    val totalTime: Long = 0,
    val startTimeStamp: Long = 0,
    val pausedAt: Long = Long.MAX_VALUE,
    val totalPauseDuration: Long = 0L
) : Parcelable