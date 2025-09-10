package com.ag.kopfrechner.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ag.kopfrechner.data.dao.AdditionTaskDao
import com.ag.kopfrechner.data.dao.DivisionTaskDao
import com.ag.kopfrechner.data.dao.MultiplicationTaskDao
import com.ag.kopfrechner.data.dao.SubtractionTaskDao

class GameViewModelFactory(
    private val additionTaskDao: AdditionTaskDao,
    private val subtractionTaskDao: SubtractionTaskDao,
    private val multiplicationTaskDao: MultiplicationTaskDao,
    private val divisionTaskDao: DivisionTaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
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
