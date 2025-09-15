/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.db

import android.content.Context
import androidx.room.Room
import java.io.File
import java.io.FileOutputStream

class DbImporter(private val context: Context) {

    fun importDb(): AppDatabase {

        val dbFileName = "math_tasks.db"
        val dbPath = context.filesDir.absolutePath + "/$dbFileName"
        val dbFile = File(dbPath)

        if (!dbFile.exists()) {
            context.assets.open(dbFileName).use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }
        }

        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            dbFile.absolutePath
        ).createFromFile(dbFile)
            .build()
    }
}