package com.example.myapplication.core

import android.app.Application
import com.example.myapplication.auth.di.viewmodel.userViewModelModule
import com.example.myapplication.carddetails.di.viewmodel.cardDetailsViewModelModule
import com.example.myapplication.cards.di.cardsViewModelModule
import com.example.myapplication.core.di.data.coreDataModule
import com.example.myapplication.core.di.domain.coreInteractorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                coreDataModule,
                coreInteractorModule,
                userViewModelModule,
                cardsViewModelModule,
                cardDetailsViewModelModule
            )
        }
    }
}