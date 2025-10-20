package com.example.myapplication.loans.data.repository

import com.example.myapplication.loans.data.resource.DataResource
import com.example.myapplication.loans.domain.model.Pay
import com.example.myapplication.loans.domain.repository.LoanRepository
import java.math.BigDecimal

class LoanRepositoryImpl(
    val dataResource: DataResource
) : LoanRepository {
    override suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay? {
        return dataResource.calculateLoan(sum = sum, percent = percent, period = period)
    }
}