/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.entity

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

