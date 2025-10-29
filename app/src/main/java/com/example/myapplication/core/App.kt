package com.example.myapplication.core

import android.app.Application
import com.example.myapplication.auth.di.data.authDataModule
import com.example.myapplication.auth.di.domain.domainModule
import com.example.myapplication.auth.di.viewmodel.authViewModelModule
import com.example.myapplication.carddetails.di.viewmodel.cardDetailsViewModelModule
import com.example.myapplication.cards.di.cardsViewModelModule
import com.example.myapplication.cards.di.data.cardsModule
import com.example.myapplication.core.di.data.coreDataModule
import com.example.myapplication.core.di.data.networkModule
import com.example.myapplication.core.di.domain.coreInteractorModule
import com.example.myapplication.creditcards.di.data.creditCardsModule
import com.example.myapplication.creditcards.di.viewmodel.creditCardsViewModelModule
import com.example.myapplication.debitcards.di.data.debitCardsModule
import com.example.myapplication.debitcards.di.viewmodel.debitCardsViewModelModule
import com.example.myapplication.deposits.di.data.depositsModule
import com.example.myapplication.loans.di.loansModule
import com.example.myapplication.loans.di.loansViewModelModule
import com.example.myapplication.loansanddeposits.di.data.loansAndDepositsModule
import com.example.myapplication.loansanddeposits.di.domain.loansAndDepositsDomainModule
import com.example.myapplication.loansanddeposits.di.viewmodel.loansAndDepositsViewModelModule
import com.example.myapplication.profile.di.profileViewModelModule
import com.example.myapplication.transfer.di.data.transferModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                cardsModule,
                coreDataModule,
                coreInteractorModule,
                authDataModule,
                domainModule,
                authViewModelModule,
                cardsViewModelModule,
                networkModule,
                creditCardsModule,
                debitCardsModule,
                depositsModule,
                loansModule,
                loansViewModelModule,
                transferModule,
                cardDetailsViewModelModule,
                creditCardsViewModelModule,
                debitCardsViewModelModule,
                profileViewModelModule,
                loansAndDepositsModule,
                loansAndDepositsDomainModule,
                loansAndDepositsViewModelModule,
            )
        }
    }
}