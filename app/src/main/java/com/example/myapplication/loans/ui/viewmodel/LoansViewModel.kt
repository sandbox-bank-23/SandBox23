package com.example.myapplication.loans.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.ui.state.InitialCharacteristics
import com.example.myapplication.loans.ui.state.LoansState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class LoansViewModel(
    val loanInteractor: Loan,
    val appInteractor: AppInteractor
) : ViewModel() {

    private val _authData = MutableStateFlow<AuthData>(
        value = AuthData(
            code = 0,
            description = "",
            accessToken = "",
            refreshToken = "",
            userId = ""
        )
    )
    private val _stateLoans = MutableStateFlow<LoansState>(value = LoansState.Default)
    val stateLoans = _stateLoans.asStateFlow()


    private val _initialCharacteristics = MutableStateFlow<InitialCharacteristics>(
        value = InitialCharacteristics(
            monthLimit = emptyList(),
            moneyLimit = emptyList()
        )
    )

    val initialCharacteristics = _initialCharacteristics.asStateFlow()

    companion object {
        const val CREDIT_LIMIT_MAX = 3_000_000
        const val CREDIT_LIMIT_MIN = 30_000
        const val STEP_CREDIT = 10_000

        const val CREDIT_NAME = "Потребительский кредит"

        private const val THREE_MONTHS = 3

        private const val SIX_MONTHS = 6

        private const val NINE_MONTHS = 9

        private const val TWELVE_MONTHS = 12

        private const val TWENTY_FOUR_MONTHS = 24

    }

    init {
        val monthLimit: List<Int> = listOf(
            THREE_MONTHS,
            SIX_MONTHS,
            NINE_MONTHS,
            TWELVE_MONTHS,
            TWENTY_FOUR_MONTHS
        )
        val trackSlider = getTrackSlider(CREDIT_LIMIT_MAX, CREDIT_LIMIT_MIN, STEP_CREDIT)
        _initialCharacteristics.value =
            InitialCharacteristics(
                monthLimit = monthLimit,
                moneyLimit = trackSlider
            )

        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { data ->
                if (data != null) {
                    _authData.value = data
                }
            }
        }
    }

    fun getTrackSlider(maxLimit: Int, minLimit: Int, stepTrack: Int): List<Int> {
        val sliderStep =
            sliderStep(maxLimit, minLimit, stepTrack)
        val trackSlider = mutableListOf<Int>(minLimit)
        for (i in 1..sliderStep) {
            trackSlider.add(stepTrack + trackSlider[i - 1])
        }
        return trackSlider
    }

    fun sliderStep(maxLimit: Int, minLimit: Int, stepTrack: Int): Int {
        return (maxLimit - minLimit) / stepTrack
    }

    fun openLoan(limit: Int, month: Int) {
        val limitKopeck = BigDecimal(limit).multiply(BigDecimal(100))
        val userId = _authData.value.userId?.toLong() ?: 0
        val credit = Credit(
            id = null,
            userId = userId,
            name = CREDIT_NAME,
            balance = limitKopeck,
            period = month.toLong(),
            orderDate = System.currentTimeMillis(),
            monthPay = null,
            endDate = null,
            percent = null,
            isClose = null
        )

        viewModelScope.launch {
            loanInteractor.create(credit).collect { stateLoans -> loanState(stateLoans) }
        }
    }

    fun loanState(sate: LoanResult) {
        when (sate) {
            is LoanResult.Error -> renderLoansState(LoansState.LimitExceeded)
            is LoanResult.Success -> renderLoansState(LoansState.LoanApproved)

        }
    }

    fun renderLoansState(state: LoansState) {
        _stateLoans.value = state
    }

}