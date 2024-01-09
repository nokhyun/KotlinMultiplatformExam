package com.nokhyun.kmmexam

import android.app.Application
import di.appModule
import di.sqldelightModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ExamApplication)
            androidLogger()
            modules(appModule() + sqldelightModule)
        }

        Napier.base(DebugAntilog())
    }
}