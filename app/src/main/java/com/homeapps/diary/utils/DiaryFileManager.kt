package com.homeapps.diary.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import java.io.File

class DiaryFileManager(private val appContext: Context) {
    private val gson = Gson()
    val storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun hasStoragePermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return true
        }
        return storagePermissions.all { ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED }
    }

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