package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ag.kopfrechner.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.random.Random

class GameViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver {

    private val _gameState = mutableStateOf(
        savedStateHandle.get<GameState>("game_state") ?: GameState()
    )
    val gamesState: State<GameState> = _gameState

    fun appendToInput(number: Int) {
        if (_gameState.value.input.length < 7) {
            if (_gameState.value.input == "0") {
                clearInput()
            }
            _gameState.value = _gameState.value.copy(input = _gameState.value.input + number)
            saveState()
        }
    }

    fun removeLastDigit() {
        if (_gameState.value.input.isNotEmpty()) {
            _gameState.value =   _gameState.value.copy(input = _gameState.value.input.dropLast(1))
            saveState()
        }
    }

    fun clearInput() {
        _gameState.value = _gameState.value.copy(input = "")
        saveState()
    }


    fun checkAnswerAndCount() {
        val correctResult = calculateResult()
        val userInput = _gameState.value.input.toIntOrNull()

        if (userInput == null) {
            _gameState.value = _gameState.value.copy(
                skippedAnswers =  _gameState.value.skippedAnswers + 1,
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
            R.string.add -> _gameState.value.operand1 + _gameState.value.operand2
            R.string.subtract -> _gameState.value.operand1 - _gameState.value.operand2
            R.string.multiply -> _gameState.value.operand1 * _gameState.value.operand2
            R.string.divide -> if (_gameState.value.operand2 != 0) _gameState.value.operand1 / _gameState.value.operand2 else 0
            else -> 0
        }
    }

    fun resetGame() {
        _gameState.value = GameState()
        saveState()
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
        saveState()
    }

    fun setEnabledOperators(settingsViewModel: SettingsViewModel) {
        val enabledOperators = mutableListOf<Pair<Int, Pair<Float, Float>>>()

        val settingsState = settingsViewModel.settingsState.value
        if (settingsState.isPlusEnabled) {
            enabledOperators.add(R.string.add to settingsViewModel.settingsState.value.plusRange)
        }
        if (settingsState.isMinusEnabled) {
            enabledOperators.add(R.string.subtract to settingsViewModel.settingsState.value.minusRange)
        }
        if (settingsState.isMultiplyEnabled) {
            enabledOperators.add(R.string.multiply to settingsViewModel.settingsState.value.multiplyRange)
        }
        if (settingsState.isDivideEnabled) {
            enabledOperators.add(R.string.divide to settingsViewModel.settingsState.value.divideRange)
        }

        _gameState.value = _gameState.value.copy(enabledOperators = enabledOperators)
        saveState()
    }

    fun generateNewTask() {
        val operator = _gameState.value.enabledOperators.random()
        val difficulty =
            Random.nextInt(operator.second.first.toInt(), operator.second.second.toInt() + 1)

        _gameState.value = _gameState.value.copy(
            operand1 = difficulty,
            operand2 = difficulty,
            operator = operator.first,
            input = "",
            isCorrect = null
        )
        saveState()
    }

    fun setEndTimestamp() {
        _gameState.value = _gameState.value.copy(endTimeStamp = Instant.now().epochSecond)
        saveState()
    }

    fun setStartTimestamp() {
        _gameState.value = _gameState.value.copy(startTimeStamp = Instant.now().epochSecond)
        saveState()
    }

    private var timerJob: Job? = null

    fun startTimer() {
        if (_gameState.value.isTimerRunning) return

        timerJob?.cancel()

        _gameState.value = _gameState.value.copy(isTimerRunning = true)
        saveState()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (_gameState.value.isTimerRunning) {
                delay(1000)
                addTime(1)
            }
        }
    }

    fun pauseTimer() {
        _gameState.value = _gameState.value.copy(isTimerRunning = false)
        saveState()
        timerJob?.cancel()  // Stoppt den laufenden Timer
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startTimer()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        pauseTimer()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        pauseTimer()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        startTimer()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        pauseTimer()
    }

    fun addTime(seconds: Int) {
        _gameState.value = _gameState.value.copy(
            activeTime = _gameState.value.activeTime + seconds
        )
        saveState()
    }

    private fun saveState() {
        savedStateHandle["game_state"] = _gameState.value
    }
}