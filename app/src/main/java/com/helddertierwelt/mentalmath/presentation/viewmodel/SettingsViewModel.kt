/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helddertierwelt.mentalmath.data.datastore.SettingsRepository
import com.helddertierwelt.mentalmath.presentation.theme.ThemeMode
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository,
) : ViewModel() {

    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

    private val _isLoaded = mutableStateOf(false)
    val isLoaded: State<Boolean> = _isLoaded

    init {
        viewModelScope.launch {
            repository.settingsFlow.collect { loadedState ->
                _settingsState.value = loadedState
                _isLoaded.value = true
            }
        }
    }

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
        viewModelScope.launch {
            repository.saveSettings(_settingsState.value)
        }
    }
}
