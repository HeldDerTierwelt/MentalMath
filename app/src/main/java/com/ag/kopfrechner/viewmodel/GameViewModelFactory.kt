package com.ag.kopfrechner.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ag.kopfrechner.data.MathTaskDao

class GameViewModelFactory(
    private val mathTaskDao: MathTaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(SavedStateHandle(), mathTaskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

