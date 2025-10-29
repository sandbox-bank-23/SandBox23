package com.example.myapplication.loans.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class LoanDetailViewModel(
    val loanId: Long,
    val loanInteractor: Loan,
) : ViewModel() {

    private val _loanData =
        MutableStateFlow<Credit>(
            value = Credit(
                id = null,
                userId = 0L,
                name = "",
                balance = BigDecimal(0),
                period = 0L,
                orderDate = 0L,
                monthPay = null,
                endDate = null,
                percent = null,
                isClose = null
            )
        )
    val loanData = _loanData.asStateFlow()

    init {
        getLoanResult()
    }

    fun getLoanResult() {
        viewModelScope.launch {
            loanInteractor.getLoan(loanId).collect { loan -> getLoan(loan) }
        }
    }

    fun onCloseClick() {
        viewModelScope.launch {
            loanInteractor.close(loanId).collect { loan -> getLoan(loan) }
        }
    }

    fun getLoan(sate: LoanResult) {
        when (sate) {
            is LoanResult.Error -> ""
            is LoanResult.Success -> _loanData.value = sate.data
        }
    }
}