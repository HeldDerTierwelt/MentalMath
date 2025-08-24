package com.ag.kopfrechner.data

import android.content.Context
import androidx.core.content.edit


class CsvImporter(
    private val context: Context,
    private val dao: MathTaskDao
) {

    private val prefsName = "app_prefs"
    private val csvImportedKey = "csv_imported"

    private fun isCsvImported(): Boolean {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return prefs.getBoolean(csvImportedKey, false)
    }

    private fun markCsvAsImported() {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(csvImportedKey, true) }
    }

    suspend fun importCsvIfNeeded() {
        if (isCsvImported()) return

        val tasks = mutableListOf<MathTask>()
        val input = context.assets.open("tasks.csv")
        input.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 6) {
                    val task = MathTask(
                        operand1 = parts[0].toInt(),
                        operand2 = parts[1].toInt(),
                        difficultyPlus = parts[2].toInt(),
                        difficultyMinus = parts[3].toInt(),
                        difficultyMultiply = parts[4].toInt(),
                        difficultyDivide = parts[5].toInt()
                    )
                    tasks.add(task)
                }
            }
        }

        dao.insertAll(tasks)
        markCsvAsImported()
    }
}