package com.example.myapplication.loansanddeposits.data.repo

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.loans.domain.repository.LoanRepository
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.random.Random

class LoansAndDepositsRepositoryImpl(
    private val loan: LoanRepository,
    private val deposits: DepositsRepository,
) : LoansAndDepositsRepository {

    override suspend fun getAllLoansAndDeposits(userId: Long): Flow<Result<List<Product>>> {
        val depFlow = deposits.observeAllDeposits()
        val loanFlow = loan.getLoanList(userId = userId)

        return combine(depFlow, loanFlow) { depItems, loanItems ->
            val newDeposit = depItems.map { item ->
                Product(
                    id = item.dbId,
                    type = item.product.type,
                    period = item.product.period,
                    percent = item.product.percent,
                    balance = item.product.balance,
                    percentType = item.product.percentType
                )
            }
            val newLoans = loanItems.map { item ->
                Product(
                    id = item.id ?: Random.nextLong(1, Long.MAX_VALUE),
                    type = ProductType.LOAN,
                    period = item.period,
                    percent = item.percent ?: Random.nextLong(1, Long.MAX_VALUE),
                    balance = item.balance.toLong(),
                    percentType = PERCENT_TYPE
                )

            }
            val result = newDeposit + newLoans
            if (result.isEmpty()) {
                Result.Error(EMPTY_LIST)
            } else {
                Result.Success(result)
            }
        }
    }

    companion object {
        private const val EMPTY_LIST = "Empty list"
        private const val PERCENT_TYPE = 2L
    }
}