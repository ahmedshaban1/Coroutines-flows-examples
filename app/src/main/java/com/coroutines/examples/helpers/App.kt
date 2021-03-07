package com.coroutines.examples.helpers

import android.app.Application
import com.coroutines.examples.di.getRemoteModule
import com.coroutines.examples.helpers.Constants.BASE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(getRemoteModule(BASE_URL))
            androidContext(this@App)
        }
    }
}