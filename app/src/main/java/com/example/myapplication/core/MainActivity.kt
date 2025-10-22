package com.example.myapplication.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.core.ui.compose.App
import com.example.myapplication.core.ui.theme.SandBox23Theme
import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    val loanInteractor: Loan by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val credit = Credit(
                userId = 777,
                balance = BigDecimal(400_000),
                period = 24L,
                orderDate = System.currentTimeMillis()
            )
            loanInteractor.create(loan = credit)
        }
        setContent {
            SandBox23Theme {
                App()
            }
        }
    }
}