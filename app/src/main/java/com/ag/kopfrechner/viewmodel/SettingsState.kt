package com.ag.kopfrechner.viewmodel

import com.ag.kopfrechner.R

data class SettingsState(
    val isModeEnabled: Boolean = true,
    val limit: Float = 5f,

    val plusRange: ClosedFloatingPointRange<Float> = 2f..5f,
    val minusRange: ClosedFloatingPointRange<Float> = 2f..5f,
    val multiplyRange: ClosedFloatingPointRange<Float> = 2f..5f,
    val divideRange: ClosedFloatingPointRange<Float> = 2f..5f,

    val isPlusEnabled: Boolean = true,
    val isMinusEnabled: Boolean = true,
    val isMultiplyEnabled: Boolean = true,
    val isDivideEnabled: Boolean = true,

    val enabledOperators: List<Int> = listOf(
        R.string.add,
        R.string.subtract,
        R.string.multiply,
        R.string.divide
    )
)