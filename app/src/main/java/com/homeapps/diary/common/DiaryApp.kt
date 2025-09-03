package com.homeapps.diary.common

import android.app.Application
import com.homeapps.diary.common.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DiaryApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DiaryApp)
            modules(appModule)
        }
    }
}