package com.ag.kopfrechner.data.entity

import com.ag.kopfrechner.data.MathTask

fun AdditionTask.toMathTask() = MathTask(
    operand1 = operand1,
    operand2 = operand2,
    difficulty = difficultyAddition
)

fun SubtractionTask.toMathTask() = MathTask(
    operand1 = operand1,
    operand2 = operand2,
    difficulty = difficultySubtraction
)

fun MultiplicationTask.toMathTask() = MathTask(
    operand1 = operand1,
    operand2 = operand2,
    difficulty = difficultyMultiplication
)

fun DivisionTask.toMathTask() = MathTask(
    operand1 = operand1,
    operand2 = operand2,
    difficulty = difficultyDivision
)

