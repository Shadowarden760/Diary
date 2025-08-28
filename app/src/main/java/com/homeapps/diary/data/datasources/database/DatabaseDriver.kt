package com.homeapps.diary.data.datasources.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.homeapps.diary.DiaryDB

class DatabaseDriver(private val appContext: Context) {

    fun createDatabaseDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = DiaryDB.Schema,
            context = appContext,
            name = "notes.db"
        )
    }

}