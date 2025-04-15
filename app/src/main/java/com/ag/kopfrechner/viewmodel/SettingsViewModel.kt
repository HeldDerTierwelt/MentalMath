package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _uiState = mutableStateOf(SettingsUiState())
    val uiState: State<SettingsUiState> = _uiState

    fun updateLimit(value: Float) {
        _uiState.value = _uiState.value.copy(limit = value)
    }

    fun updatePlusRange(plusRange:  ClosedFloatingPointRange<Float>) {
        _uiState.value = _uiState.value.copy(plusRange = plusRange)
    }

    fun updateMinusRange(minusRange:  ClosedFloatingPointRange<Float>) {
        _uiState.value = _uiState.value.copy(minusRange = minusRange)
    }

    fun updateMultiplyRange(multiplyRange:  ClosedFloatingPointRange<Float>) {
        _uiState.value = _uiState.value.copy(multiplyRange = multiplyRange)
    }

    fun updateDivideRange(divideRange:  ClosedFloatingPointRange<Float>) {
        _uiState.value = _uiState.value.copy(divideRange = divideRange)
    }

    fun toggleMode() {
        _uiState.value = _uiState.value.copy(isModeEnabled = !_uiState.value.isModeEnabled)
    }

    fun togglePlus() {
        _uiState.value = _uiState.value.copy(isPlusEnabled = !_uiState.value.isPlusEnabled)
    }

    fun toggleMinus() {
        _uiState.value = _uiState.value.copy(isMinusEnabled = !_uiState.value.isMinusEnabled)
    }

    fun toggleMultiply() {
        _uiState.value = _uiState.value.copy(isMultiplyEnabled = !_uiState.value.isMultiplyEnabled)
    }

    fun toggleDivide() {
        _uiState.value = _uiState.value.copy(isDivideEnabled = !_uiState.value.isDivideEnabled)
    }
}