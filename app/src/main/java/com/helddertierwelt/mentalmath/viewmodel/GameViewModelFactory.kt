/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helddertierwelt.mentalmath.data.dao.AdditionTaskDao
import com.helddertierwelt.mentalmath.data.dao.DivisionTaskDao
import com.helddertierwelt.mentalmath.data.dao.MultiplicationTaskDao
import com.helddertierwelt.mentalmath.data.dao.SubtractionTaskDao

class GameViewModelFactory(
    private val additionTaskDao: AdditionTaskDao,
    private val subtractionTaskDao: SubtractionTaskDao,
    private val multiplicationTaskDao: MultiplicationTaskDao,
    private val divisionTaskDao: DivisionTaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(
                SavedStateHandle(),
                additionTaskDao,
                subtractionTaskDao,
                multiplicationTaskDao,
                divisionTaskDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
