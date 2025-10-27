package com.example.myapplication.loansanddeposits.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.loans.domain.repository.LoanRepository
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import kotlinx.serialization.json.Json
import kotlin.random.Random

class LoansAndDepositsRepositoryImpl(
    private val client: NetworkClient,
    private val loansAndDepositsMock: LoansAndDepositsMock,
    private val json: Json = Json,
    private val loan: LoanRepository,
    private val deposits: DepositsRepository,
) : LoansAndDepositsRepository {

    override suspend fun getAllLoansAndDeposits(userId: Long): Result<List<Product>> {
        //val deposits = deposits.getDeposits()
        //val loans = loan.getLoan()
        val items = mutableListOf<Product>()
        loan.getLoanList(userId = userId).collect { loanItems ->
            loanItems.forEach { item ->
                items.add(
                    Product(
                        id = item.id ?: Random.nextLong(1, Long.MAX_VALUE),
                        type = ProductType.LOAN,
                        period = item.period,
                        percent = item.percent ?: Random.nextLong(1, Long.MAX_VALUE),
                        balance = item.balance.toLong(),
                        percentType = PERCENT_TYPE
                    )
                )
            }
        }
        deposits.getDeposits().forEach { deposit ->
            items.add(
                Product(
                    id = Random.nextLong(1, Long.MAX_VALUE),
                    type = deposit.product.type,
                    period = deposit.product.period,
                    percent = deposit.product.percent,
                    balance = deposit.product.balance,
                    percentType = deposit.product.percentType
                )
            )
        }
        //val raw = client(loansAndDepositsMock.getLoansAndDeposits())
        return if (items.isNotEmpty()) {
            Result.Success(items.toList())
        } else {
            Result.Error(ApiCodes.EMPTY_BODY)
        }
    }
    
    companion object {
        private const val PERCENT_TYPE = 2L
    }
}