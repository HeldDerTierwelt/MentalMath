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

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helddertierwelt.mentalmath.data.dao.AdditionTaskDao
import com.helddertierwelt.mentalmath.data.dao.DivisionTaskDao
import com.helddertierwelt.mentalmath.data.dao.MultiplicationTaskDao
import com.helddertierwelt.mentalmath.data.dao.SubtractionTaskDao
import com.helddertierwelt.mentalmath.data.entity.MathTask
import com.helddertierwelt.mentalmath.data.entity.toMathTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val additionTaskDao: AdditionTaskDao,
    private val subtractionTaskDao: SubtractionTaskDao,
    private val multiplicationTaskDao: MultiplicationTaskDao,
    private val divisionTaskDao: DivisionTaskDao
) : ViewModel(), DefaultLifecycleObserver {
    private val _gameState = mutableStateOf(
        savedStateHandle.get<GameState>("game_state") ?: GameState()
    )
    private var timerJob: Job? = null

    val gamesState: State<GameState> = _gameState

    fun appendToInput(number: Int) {
        if (_gameState.value.input.length < 8) {
            if (_gameState.value.input == "0") {
                clearInput()
            }
            _gameState.value = _gameState.value.copy(input = _gameState.value.input + number)
            saveState()
        }
    }

    fun removeLastDigit() {
        if (_gameState.value.input.isNotEmpty()) {
            _gameState.value = _gameState.value.copy(input = _gameState.value.input.dropLast(1))
            saveState()
        }
    }

    fun clearInput() {
        _gameState.value = _gameState.value.copy(input = "")
        saveState()
    }

    fun generateNewTask() {
        clearInput()
        viewModelScope.launch(Dispatchers.IO) {
            val operationSetting = _gameState.value.enabledOperators.entries.random()
            val operation = operationSetting.key
            val difficulty = Random.nextInt(
                    operationSetting.value.first.toInt(),
                    operationSetting.value.second.toInt() + 1
            )

            val mathTask: MathTask? = when (operation) {
                Operation.ADDITION -> additionTaskDao.getAdditionTasksByDifficulty(difficulty)
                    .getOrNull(0)?.toMathTask()

                Operation.SUBTRACTION -> subtractionTaskDao.getSubtractionTasksByDifficulty(difficulty)
                    .getOrNull(0)?.toMathTask()

                Operation.MULTIPLICATION -> multiplicationTaskDao.getMultiplicationTasksByDifficulty(difficulty)
                    .getOrNull(0)?.toMathTask()

                Operation.DIVISION -> divisionTaskDao.getDivisionTasksByDifficulty(difficulty)
                    .getOrNull(0)?.toMathTask()
            }

            if (mathTask != null) {
                val operand1 = if (operation == Operation.DIVISION) {
                    mathTask.operand1 * mathTask.operand2
                } else {
                    mathTask.operand1
                }
                val operand2 = if (operation == Operation.DIVISION) {
                    mathTask.operand1
                } else {
                    mathTask.operand2
                }
                _gameState.value = _gameState.value.copy(
                    operand1 = operand1,
                    operand2 = operand2,
                    operator = operation,
                    input = "",
                    isCorrect = null
                )
            } else {
                _gameState.value = _gameState.value.copy(
                    operand1 = difficulty,
                    operand2 = difficulty,
                    operator = operation,
                    input = "",
                    isCorrect = null
                )
            }
            saveState()
        }
    }

    fun checkAnswerAndCount() {
        val correctResult = calculateResult()
        val userInput = _gameState.value.input.toIntOrNull()

        if (userInput == null) {
            _gameState.value = _gameState.value.copy(
                skippedAnswers = _gameState.value.skippedAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1,
                isCorrect = null
            )
        } else if (userInput == correctResult) {
            _gameState.value = _gameState.value.copy(
                correctAnswers = _gameState.value.correctAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1,
                isCorrect = true
            )
        } else {
            _gameState.value = _gameState.value.copy(
                wrongAnswers = _gameState.value.wrongAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1,
                isCorrect = false
            )
        }
        saveState()
    }

    private fun calculateResult(): Int {
        return when (_gameState.value.operator) {
            Operation.ADDITION -> _gameState.value.operand1 + _gameState.value.operand2
            Operation.SUBTRACTION -> _gameState.value.operand1 - _gameState.value.operand2
            Operation.MULTIPLICATION -> _gameState.value.operand1 * _gameState.value.operand2
            Operation.DIVISION -> if (_gameState.value.operand2 != 0) _gameState.value.operand1 / _gameState.value.operand2 else 0
        }
    }

    fun addTaskResultToList() {
        val taskResult = TaskResult(
            taskNumber = _gameState.value.tasks.size + 1,
            operand1 = _gameState.value.operand1,
            operand2 = _gameState.value.operand2,
            operator = _gameState.value.operator,
            correctResult = calculateResult(),
            userInput = _gameState.value.input
        )
        _gameState.value = _gameState.value.copy(
            tasks = _gameState.value.tasks + taskResult
        )
        saveState()
    }

    fun setEnabledOperators(settingsViewModel: SettingsViewModel) {
        val enabledOperators: MutableMap<Operation, Pair<Float, Float>> = mutableMapOf()

        val settingsState = settingsViewModel.settingsState.value
        if (settingsState.isPlusEnabled) {
            enabledOperators.put(Operation.ADDITION, settingsViewModel.settingsState.value.plusRange)
        }
        if (settingsState.isMinusEnabled) {
            enabledOperators.put(Operation.SUBTRACTION,settingsViewModel.settingsState.value.minusRange)
        }
        if (settingsState.isMultiplyEnabled) {
            enabledOperators.put(Operation.MULTIPLICATION,settingsViewModel.settingsState.value.multiplyRange)
        }
        if (settingsState.isDivideEnabled) {
            enabledOperators.put(Operation.DIVISION,settingsViewModel.settingsState.value.divideRange)
        }

        _gameState.value = _gameState.value.copy(enabledOperators = enabledOperators)
        saveState()
    }

    fun startGame() {
        _gameState.value = _gameState.value.copy(isGameStarted = true)
        generateNewTask()
        setStartTimestamp()
        startTimer()
        saveState()
    }

    fun endGame() {
        pauseTimer()
        _gameState.value = _gameState.value.copy(isGameStarted = false)
        saveState()
    }

    fun resetGame() {
        _gameState.value = GameState()
        saveState()
    }

    fun setStartTimestamp() {
        _gameState.value = _gameState.value.copy(startTimeStamp = System.currentTimeMillis())
        saveState()
    }

    fun startTimer() {
        if (_gameState.value.isTimerRunning || !_gameState.value.isGameStarted) return

        timerJob?.cancel()
        _gameState.value = _gameState.value.copy(isTimerRunning = true)
        saveState()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (_gameState.value.isTimerRunning) {
                val elapsedTime = System.currentTimeMillis() - _gameState.value.startTimeStamp
                _gameState.value = _gameState.value.copy(
                    activeTime = elapsedTime - _gameState.value.totalPauseDuration,
                    totalTime = elapsedTime
                )
                saveState()
                delay(100)
            }
        }

    }

    fun pauseTimer() {
        _gameState.value = _gameState.value.copy(
            pausedAt = System.currentTimeMillis(),
            isTimerRunning = false
        )
        saveState()
        timerJob?.cancel()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        if (_gameState.value.isGameStarted && _gameState.value.isTimerRunning) {
            pauseTimer()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (_gameState.value.isGameStarted && !_gameState.value.isTimerRunning) {
            val totalPauseDuration =
                _gameState.value.totalPauseDuration + (System.currentTimeMillis() - _gameState.value.pausedAt)
            _gameState.value = _gameState.value.copy(totalPauseDuration = totalPauseDuration)
            saveState()
            startTimer()
        }
    }

    private fun saveState() {
        savedStateHandle["game_state"] = _gameState.value
    }
}