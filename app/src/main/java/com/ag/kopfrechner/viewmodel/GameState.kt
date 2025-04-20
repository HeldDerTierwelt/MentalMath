package com.ag.kopfrechner.viewmodel

import com.ag.kopfrechner.R

data class GameState(
    val operand1: Int = 0,
    val operand2: Int = 0,
    val operator: Int = R.string.add,
    val input: String = "",
    val tasks: List<TaskResult> = emptyList(),
    val enabledOperators: List<Pair<Int, ClosedFloatingPointRange<Float>>> = emptyList(),
    val isCorrect: Boolean? = null,

    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val skippedAnswers: Int = 0,
    val totalAnswers: Int = 0,

    val activeTime: Int = 0,
    val totalTime: Int = 0,

    val isGameOver: Boolean = false,
)