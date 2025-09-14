/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.viewmodel

import android.os.Parcelable
import com.helddertierwelt.mentalmath.R
import kotlinx.parcelize.Parcelize

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

@Parcelize
data class SettingsState(
    val isModeEnabled: Boolean = true,
    val limit: Float = 3f,

    val plusRange: Pair<Float, Float> = 2f to 5f,
    val minusRange: Pair<Float, Float> = 2f to 5f,
    val multiplyRange: Pair<Float, Float> = 2f to 5f,
    val divideRange: Pair<Float, Float> = 2f to 5f,

    val isPlusEnabled: Boolean = true,
    val isMinusEnabled: Boolean = true,
    val isMultiplyEnabled: Boolean = true,
    val isDivideEnabled: Boolean = true,

    val enabledOperators: List<Int> = listOf(
        R.string.add,
        R.string.subtract,
        R.string.multiply,
        R.string.divide
    ),

    val isSheetOpen: Boolean = false,

    val themeMode: ThemeMode = ThemeMode.SYSTEM
) : Parcelable