package com.example.myapplication.core

import android.app.Application
import com.example.myapplication.core.di.data.coreDataModule
import com.example.myapplication.core.di.domain.coreInteractorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                coreDataModule,
                coreInteractorModule,
            )
        }
    }
}