package com.example.myapplication.deposits.di.domain

import com.example.myapplication.deposits.domain.usecase.impl.CloseDepositUseCaseImpl
import com.example.myapplication.deposits.domain.usecase.impl.GetProductsUseCaseImpl
import com.example.myapplication.deposits.domain.usecase.impl.OpenDepositUseCaseImpl
import com.example.myapplication.deposits.domain.usecase.impl.TakeDepositUseCaseImpl
import com.example.myapplication.deposits.domain.usecase.inter.CloseDepositUseCase
import com.example.myapplication.deposits.domain.usecase.inter.GetProductsUseCase
import com.example.myapplication.deposits.domain.usecase.inter.OpenDepositUseCase
import com.example.myapplication.deposits.domain.usecase.inter.TakeDepositUseCase
import org.koin.dsl.module

val depositDomainModule = module {

    single<OpenDepositUseCase> {
        OpenDepositUseCaseImpl(get())
    }

    single<CloseDepositUseCase> {
        CloseDepositUseCaseImpl(get())
    }

    single<TakeDepositUseCase>{
        TakeDepositUseCaseImpl(get())
    }

    single<GetProductsUseCase> {
        GetProductsUseCaseImpl(get())
    }
}