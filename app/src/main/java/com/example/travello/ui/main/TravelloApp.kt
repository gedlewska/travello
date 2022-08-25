package com.example.travello.ui.main

import android.app.Application
import com.example.travello.ui.main.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TravelloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@TravelloApp)
            modules(appModule())
        }
    }

    private fun appModule() = appComponent

}