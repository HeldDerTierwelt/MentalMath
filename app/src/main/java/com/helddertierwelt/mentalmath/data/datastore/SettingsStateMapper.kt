/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
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