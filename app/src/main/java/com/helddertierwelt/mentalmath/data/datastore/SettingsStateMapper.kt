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

package com.helddertierwelt.mentalmath.data.datastore

import com.helddertierwelt.mentalmath.SettingsStateProto
import com.helddertierwelt.mentalmath.SettingsStateProto.ThemeMode.DARK
import com.helddertierwelt.mentalmath.SettingsStateProto.ThemeMode.LIGHT
import com.helddertierwelt.mentalmath.SettingsStateProto.ThemeMode.SYSTEM
import com.helddertierwelt.mentalmath.SettingsStateProto.ThemeMode.UNRECOGNIZED
import com.helddertierwelt.mentalmath.presentation.theme.ThemeMode
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsState

fun SettingsState.toProto(): SettingsStateProto {
    return SettingsStateProto.newBuilder()
        .setIsModeEnabled(isModeEnabled)
        .setLimit(limit)
        .setPlusMin(plusRange.first)
        .setPlusMax(plusRange.second)
        .setMinusMin(minusRange.first)
        .setMinusMax(minusRange.second)
        .setMultiplyMin(multiplyRange.first)
        .setMultiplyMax(multiplyRange.second)
        .setDivideMin(divideRange.first)
        .setDivideMax(divideRange.second)
        .setIsPlusEnabled(isPlusEnabled)
        .setIsMinusEnabled(isMinusEnabled)
        .setIsMultiplyEnabled(isMultiplyEnabled)
        .setIsDivideEnabled(isDivideEnabled)
        .setIsSheetOpen(isSheetOpen)
        .setThemeMode(
            when (themeMode) {
                ThemeMode.SYSTEM -> SYSTEM
                ThemeMode.LIGHT -> LIGHT
                ThemeMode.DARK -> DARK
            }
        )
        .build()
}

fun SettingsStateProto.toSettingsState(): SettingsState {
    return SettingsState(
        isModeEnabled = isModeEnabled,
        limit = limit,
        plusRange = plusMin to plusMax,
        minusRange = minusMin to minusMax,
        multiplyRange = multiplyMin to multiplyMax,
        divideRange = divideMin to divideMax,
        isPlusEnabled = isPlusEnabled,
        isMinusEnabled = isMinusEnabled,
        isMultiplyEnabled = isMultiplyEnabled,
        isDivideEnabled = isDivideEnabled,
        isSheetOpen = isSheetOpen,
        themeMode = when (themeMode) {
            LIGHT -> ThemeMode.LIGHT
            DARK -> ThemeMode.DARK
            SYSTEM -> ThemeMode.SYSTEM
            UNRECOGNIZED -> ThemeMode.SYSTEM
        }
    )
}