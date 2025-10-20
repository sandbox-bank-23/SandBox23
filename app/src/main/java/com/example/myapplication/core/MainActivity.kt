package com.example.myapplication.core

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.core.ui.compose.App
import com.example.myapplication.core.ui.theme.SandBox23Theme
import com.example.myapplication.loans.domain.interactor.Loan
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    private val loanInteractor : Loan by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val sum = BigDecimal(200_000).setScale(2)
            val period = 24L
            val pay = loanInteractor.calculatePay(loanSum = sum, period = period)
            pay?.let {
                Log.d("CHECK_PAY", "$it")
            }
        }
        setContent {
            SandBox23Theme {
                App()
            }
        }
    }
}