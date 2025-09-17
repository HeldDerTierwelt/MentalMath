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
import com.helddertierwelt.mentalmath.presentation.theme.ThemeMode
import kotlinx.parcelize.Parcelize

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

    val themeMode: ThemeMode = ThemeMode.SYSTEM
) : Parcelable