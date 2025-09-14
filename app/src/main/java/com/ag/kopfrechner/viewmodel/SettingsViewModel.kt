/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SettingsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _settingsState = mutableStateOf(
        savedStateHandle.get<SettingsState>("settings_state") ?: SettingsState()
    )
    val settingsState: State<SettingsState> = _settingsState

    fun updateLimit(value: Float) {
        _settingsState.value = _settingsState.value.copy(limit = value)
        saveState()
    }

    fun updatePlusRange(plusRange: ClosedFloatingPointRange<Float>) {
        _settingsState.value =
            _settingsState.value.copy(plusRange = plusRange.start to plusRange.endInclusive)
        saveState()
    }

    fun updateMinusRange(minusRange: ClosedFloatingPointRange<Float>) {
        _settingsState.value =
            _settingsState.value.copy(minusRange = minusRange.start to minusRange.endInclusive)
        saveState()
    }

    fun updateMultiplyRange(multiplyRange: ClosedFloatingPointRange<Float>) {
        _settingsState.value =
            _settingsState.value.copy(multiplyRange = multiplyRange.start to multiplyRange.endInclusive)
        saveState()
    }

    fun updateDivideRange(divideRange: ClosedFloatingPointRange<Float>) {
        _settingsState.value =
            _settingsState.value.copy(divideRange = divideRange.start to divideRange.endInclusive)
        saveState()
    }

    fun toggleMode() {
        _settingsState.value =
            _settingsState.value.copy(isModeEnabled = !_settingsState.value.isModeEnabled)
        saveState()
    }

    fun togglePlus() {
        _settingsState.value =
            _settingsState.value.copy(isPlusEnabled = !_settingsState.value.isPlusEnabled)
        saveState()
    }

    fun toggleMinus() {
        _settingsState.value =
            _settingsState.value.copy(isMinusEnabled = !_settingsState.value.isMinusEnabled)
        saveState()
    }

    fun toggleMultiply() {
        _settingsState.value =
            _settingsState.value.copy(isMultiplyEnabled = !_settingsState.value.isMultiplyEnabled)
        saveState()
    }

    fun toggleDivide() {
        _settingsState.value =
            _settingsState.value.copy(isDivideEnabled = !_settingsState.value.isDivideEnabled)
        saveState()
    }

    fun toggleSheetOpen() {
        _settingsState.value =
            _settingsState.value.copy(isSheetOpen = !_settingsState.value.isSheetOpen)
        saveState()
    }

    fun setThemeMode(mode: ThemeMode) {
        _settingsState.value = _settingsState.value.copy(themeMode = mode)
        saveState()
    }

    private fun saveState() {
        savedStateHandle["settings_state"] = _settingsState.value
    }
}
