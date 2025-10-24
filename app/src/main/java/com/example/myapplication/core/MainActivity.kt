package com.example.myapplication.core

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.ui.compose.App
import com.example.myapplication.core.ui.theme.SandBox23Theme
import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.CREDIT_NAME
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.math.BigDecimal
import kotlin.getValue

class MainActivity : ComponentActivity() {

    val loanInteractor: Loan by inject()
    val appInteractor: AppInteractor by inject()

    private suspend fun testCreate() {
        appInteractor.getAuthDataValue(storageKey = StorageKey.AUTHDATA).collect { authData ->
            authData?.let { data ->
                val userId = data.userId
                userId?.let {
                    val credit = Credit(
                        userId = it.toLong(),
                        balance = BigDecimal(400_000),
                        period = 24L,
                        orderDate = System.currentTimeMillis(),
                        name = CREDIT_NAME,
                        monthPay = BigDecimal(0)
                    )
                    loanInteractor.create(loan = credit).collect { result ->
                        when (result) {
                            is LoanResult.Error -> {
                                val cr = result.error
                                Log.d("LOAN_CREATE", """
                                        "status: $cr"
                                    """.trimIndent())
                            }
                            is LoanResult.Success -> {
                                val cr = result.data
                                Log.d("LOAN_CREATE", """
                                        "loan_id: ${cr.id}"
                                        "userId: ${cr.userId}"
                                        "percent: ${cr.percent}"
                                        "balance: ${cr.balance}"
                                        "monthPay: ${cr.monthPay.toString()}"
                                    """.trimIndent())
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun testGetLoans() {
        appInteractor.getAuthDataValue(storageKey = StorageKey.AUTHDATA).collect { authData ->
            authData?.let { data ->
                val userId = data.userId
                userId?.let {
                    loanInteractor.getLoanList(userId = userId.toLong()).collect { result ->
                        result.forEach { loan ->
                            Log.d(
                                "LOAN_LIST",
                                """
                                        "status: ${loan.id}"
                                        "userId: ${loan.userId}"
                                        "percent: ${loan.percent}"
                                        "isClose: ${loan.isClose}"
                                        "name: ${loan.name}"
                                        "monthPay: ${loan.monthPay}"
                                    """
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            testCreate()
            //testGetLoans()
        }
        setContent {
            SandBox23Theme {
                App()
            }
        }
    }
}