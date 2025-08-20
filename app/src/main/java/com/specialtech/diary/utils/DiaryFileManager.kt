package com.specialtech.diary.utils

import android.os.Environment
import com.google.gson.Gson
import java.io.File

class DiaryFileManager {
    private val gson = Gson()

    fun <T> saveDataToFile(data: T, fileName: String): String? {
        return runCatching {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val newFile = File(downloadDir, fileName)
            val jsonString = gson.toJson(data)
            newFile.writeText(jsonString)
            newFile.absolutePath
        }.getOrElse { exception ->
            exception.printStackTrace()
            null
        }
    }

    fun deleteFile(filePath: String?): Boolean {
        filePath?.let {
            val file = File(filePath)
            return if (file.exists()) file.delete() else true
        }
        return true
    }
}