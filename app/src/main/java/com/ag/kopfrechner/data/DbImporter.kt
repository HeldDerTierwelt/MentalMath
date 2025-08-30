package com.ag.kopfrechner.data

import android.content.Context
import androidx.room.Room
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.GZIPInputStream

class DbImporter(private val context: Context) {

    fun importDb(): AppDatabase {

        val gzAssetName = "math_tasks.db.bin"
        val dbFilePath = context.filesDir.absolutePath + "/math_tasks.db"

        val dbFile = context.getFileStreamPath("math_tasks.db")
        if (!dbFile.exists()) {
            context.assets.open(gzAssetName).use { gzStream ->
                decompressGzFile(gzStream, dbFilePath)
            }
        }

        return  Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                dbFilePath
            )
            .createFromFile(dbFile)
            .build()
    }

    private fun decompressGzFile(gzInputStream: InputStream, outputFilePath: String) {
        val buffer = ByteArray(8192)

        try {
            GZIPInputStream(gzInputStream).use { gzip ->
                FileOutputStream(outputFilePath).use { fos ->
                    var bytesRead: Int
                    while (gzip.read(buffer).also { bytesRead = it } != -1) {
                        fos.write(buffer, 0, bytesRead)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
