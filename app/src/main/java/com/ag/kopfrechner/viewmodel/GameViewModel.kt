package com.ag.kopfrechner.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ag.kopfrechner.R
import com.ag.kopfrechner.data.MathTaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mathTaskDao: MathTaskDao
) : ViewModel(), DefaultLifecycleObserver {
    private val _gameState = mutableStateOf(
        savedStateHandle.get<GameState>("game_state") ?: GameState()
    )
    private var timerJob: Job? = null

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
            _gameState.value = _gameState.value.copy(input = _gameState.value.input.dropLast(1))
            saveState()
        }
    }


    fun clearInput() {
        _gameState.value = _gameState.value.copy(input = "")
        saveState()
    }

    fun generateNewTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val operatorSetting = _gameState.value.enabledOperators.random()
            val operator = operatorSetting.first
            val difficulty =
                Random.nextInt(operatorSetting.second.first.toInt(), operatorSetting.second.second.toInt() + 1)

            val task = when (operator) {
                R.string.add -> mathTaskDao.getAdditionTasksByDifficulty(difficulty).getOrNull(0)
                R.string.subtract -> mathTaskDao.getSubtractionTasksByDifficulty(difficulty).getOrNull(0)
                R.string.multiply -> mathTaskDao.getMultiplicationTasksByDifficulty(difficulty).getOrNull(0)
                R.string.divide -> mathTaskDao.getDivisionTasksByDifficulty(difficulty).getOrNull(0)
                else -> null
            }

            if (task != null) {
                _gameState.value = _gameState.value.copy(
                    operand1 = task.operand1,
                    operand2 = task.operand2,
                    operator = operator,
                    input = "",
                    isCorrect = null
                )
            } else {
                _gameState.value = _gameState.value.copy(
                    operand1 = difficulty,
                    operand2 = difficulty,
                    operator = operator,
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
            R.string.add -> _gameState.value.operand1 + _gameState.value.operand2
            R.string.subtract -> _gameState.value.operand1 - _gameState.value.operand2
            R.string.multiply -> _gameState.value.operand1 * _gameState.value.operand2
            R.string.divide -> if (_gameState.value.operand2 != 0) _gameState.value.operand1 / _gameState.value.operand2 else 0
            else -> 0
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
                _gameState.value = _gameState.value.copy(activeTime = elapsedTime - _gameState.value.totalPauseDuration, totalTime = elapsedTime)
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

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        if (_gameState.value.isGameStarted && _gameState.value.isTimerRunning) {
            pauseTimer()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (_gameState.value.isGameStarted && !_gameState.value.isTimerRunning) {
            val totalPauseDuration = _gameState.value.totalPauseDuration + (System.currentTimeMillis() - _gameState.value.pausedAt)
            _gameState.value = _gameState.value.copy(totalPauseDuration = totalPauseDuration)
            saveState()
            startTimer()
        }
    }


    private fun saveState() {
        savedStateHandle["game_state"] = _gameState.value
    }
}