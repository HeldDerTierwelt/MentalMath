package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

    fun updateLimit(value: Float) {
        _settingsState.value = _settingsState.value.copy(limit = value)
    }

    fun updatePlusRange(plusRange:  ClosedFloatingPointRange<Float>) {
        _settingsState.value = _settingsState.value.copy(plusRange = plusRange)
    }

    fun updateMinusRange(minusRange:  ClosedFloatingPointRange<Float>) {
        _settingsState.value = _settingsState.value.copy(minusRange = minusRange)
    }

    fun updateMultiplyRange(multiplyRange:  ClosedFloatingPointRange<Float>) {
        _settingsState.value = _settingsState.value.copy(multiplyRange = multiplyRange)
    }

    fun updateDivideRange(divideRange:  ClosedFloatingPointRange<Float>) {
        _settingsState.value = _settingsState.value.copy(divideRange = divideRange)
    }

    fun toggleMode() {
        _settingsState.value = _settingsState.value.copy(isModeEnabled = !_settingsState.value.isModeEnabled)
    }

    fun togglePlus() {
        _settingsState.value = _settingsState.value.copy(isPlusEnabled = !_settingsState.value.isPlusEnabled)
    }

    fun toggleMinus() {
        _settingsState.value = _settingsState.value.copy(isMinusEnabled = !_settingsState.value.isMinusEnabled)
    }

    fun toggleMultiply() {
        _settingsState.value = _settingsState.value.copy(isMultiplyEnabled = !_settingsState.value.isMultiplyEnabled)
    }

    fun toggleDivide() {
        _settingsState.value = _settingsState.value.copy(isDivideEnabled = !_settingsState.value.isDivideEnabled)
    }
}