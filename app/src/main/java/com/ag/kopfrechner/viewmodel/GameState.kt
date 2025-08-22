package com.ag.kopfrechner.viewmodel

import android.os.Parcelable
import com.ag.kopfrechner.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameState(
    val operand1: Int = 0,
    val operand2: Int = 0,
    val operator: Int = R.string.add,
    val input: String = "",
    val tasks: List<TaskResult> = emptyList(),
    val enabledOperators: List<Pair<Int, Pair<Float, Float>>> = emptyList(),
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