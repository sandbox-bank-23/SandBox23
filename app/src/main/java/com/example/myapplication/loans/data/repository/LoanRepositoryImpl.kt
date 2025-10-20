package com.example.myapplication.loans.data.repository

import com.example.myapplication.loans.data.resource.DataResource
import com.example.myapplication.core.domain.models.loan.Pay
import com.example.myapplication.core.domain.models.loan.Percent
import com.example.myapplication.loans.domain.repository.LoanRepository
import java.math.BigDecimal

class LoanRepositoryImpl(
    val dataResource: DataResource
) : LoanRepository {
    override suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay? {
        return dataResource.calculateLoan(sum = sum, percent = percent, period = period)
    }

    override suspend fun getPercent(): Percent? {
        return dataResource.getPercent()
    }
}