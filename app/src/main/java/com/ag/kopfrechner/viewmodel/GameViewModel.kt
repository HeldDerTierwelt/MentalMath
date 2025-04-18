package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ag.kopfrechner.R

class GameViewModel : ViewModel() {

    private val _gameState = mutableStateOf(GameState())
    val gamesState: State<GameState> = _gameState

    fun appendToInput(number: Int) {
        if (_gameState.value.input.length < 9) {
            if (_gameState.value.input == "0") {
                clearInput()
            }
            _gameState.value = _gameState.value.copy(input = _gameState.value.input + number)
        }
    }

    fun removeLastDigit() {
        if (_gameState.value.input.isNotEmpty()) {
            _gameState.value = _gameState.value.copy(input = _gameState.value.input.dropLast(1))
        }
    }

    fun clearInput() {
        _gameState.value = _gameState.value.copy(input = "")
    }


    fun checkAnswer() {
        val correctResult = calculateResult()
        val userInput = _gameState.value.input.toIntOrNull()

        if (userInput == null) {
            _gameState.value =_gameState.value.copy(
                skippedAnswers = _gameState.value.skippedAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1
            )
        } else if (userInput == correctResult) {
            _gameState.value =_gameState.value.copy(
                correctAnswers = _gameState.value.correctAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1
            )
        } else {
            _gameState.value =_gameState.value.copy(
                wrongAnswers = _gameState.value.wrongAnswers + 1,
                totalAnswers = _gameState.value.totalAnswers + 1
            )
        }
    }

    private fun calculateResult(): Int {
        return when (_gameState.value.operator) {
            R.string.add -> _gameState.value.operand1 + _gameState.value.operand2
            R.string.subtract -> _gameState.value.operand1 - _gameState.value.operand2
            R.string.multiply -> _gameState.value.operand1 * _gameState.value.operand2
            R.string.divide -> if (_gameState.value.operand2 != 0) _gameState.value.operand1 / _gameState.value.operand2 else 0
            else -> 0
        }
    }


    fun addTime(seconds: Int = 1) {
        _gameState.value = _gameState.value.copy(
            activeTime = _gameState.value.activeTime + seconds,
            totalTime = _gameState.value.totalTime + seconds
        )
    }

    fun resetGame() {
        _gameState.value = GameState()
    }

    fun addTaskResultToList() {
        val taskResult = TaskResult(
            operand1 = _gameState.value.operand1,
            operand2 = _gameState.value.operand2,
            operator = _gameState.value.operator,
            correctResult = calculateResult(),
            userInput = _gameState.value.input
        )
        _gameState.value = _gameState.value.copy(
            tasks = _gameState.value.tasks + taskResult
        )
    }

    fun generateNewTask() {
        val op1 = (0..100).random()
        val op2 = (0..100).random()
        val operator = listOf(R.string.add, R.string.subtract, R.string.multiply, R.string.divide).random()

        _gameState.value = _gameState.value.copy(
            operand1 = op1,
            operand2 = op2,
            operator = operator,
            input = ""
        )
    }

/*

    private fun generateNewTask(previousState: GameUiState): GameUiState {
        val op1 = Random.nextInt(10, 100)
        val op2 = Random.nextInt(10, 100)
        val operator = listOf('+', '-', '*').random()

        return previousState.copy(
            operand1 = op1,
            operand2 = op2,
            operator = operator,
            input = ""
        )
    }*/
}