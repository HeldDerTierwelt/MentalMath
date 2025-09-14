/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.ag.kopfrechner.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskResult(
    val taskNumber: Int,
    val operand1: Int,
    val operand2: Int,
    val operator: Int,
    val correctResult: Int,
    val userInput: String,
) : Parcelable