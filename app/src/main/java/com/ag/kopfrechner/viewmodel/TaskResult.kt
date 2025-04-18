package com.ag.kopfrechner.viewmodel

data class TaskResult(
    val operand1: Int,
    val operand2: Int,
    val operator: Int,
    val correctResult: Int,
    val userInput: String,
    )