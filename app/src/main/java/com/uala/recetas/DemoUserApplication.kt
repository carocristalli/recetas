package com.uala.recetas

import android.app.Application
import com.uala.recetas.data.module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DemoUserApplication)
            modules(appModules)
        }
    }
}