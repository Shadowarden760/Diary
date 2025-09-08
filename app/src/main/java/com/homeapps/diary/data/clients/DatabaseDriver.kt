package com.homeapps.diary.data.clients

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.homeapps.diary.DiaryDB

class DatabaseDriver(private val appContext: Context) {

    fun createDatabaseDriver(): SqlDriver {
        val driver = AndroidSqliteDriver(
            schema = DiaryDB.Schema.synchronous(),
            context = appContext,
            name = "notes.db"
        )
        makeMigrations(driver = driver)
        return driver
    }

    private fun makeMigrations(driver: AndroidSqliteDriver) {
        runCatching {
            DiaryDB.Schema.migrate(driver = driver, oldVersion = 0, newVersion = 1)
        }.getOrElse { exception ->
            print(exception.stackTrace)
        }

        runCatching {
            DiaryDB.Schema.migrate(driver = driver, oldVersion = 1, newVersion = DiaryDB.Schema.version)
        }.getOrElse { exception ->
            print(exception.stackTrace)
        }
    }
}