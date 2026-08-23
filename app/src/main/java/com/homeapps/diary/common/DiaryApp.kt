package com.homeapps.diary.common

import android.app.Application
import com.homeapps.diary.common.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DiaryApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@DiaryApp)
            modules(appModule)
        }
    }
}