package com.example.myapplication.loans.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.loans.ui.state.InitialCharacteristics
import com.example.myapplication.loans.ui.state.LoansState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoansViewModel(

) : ViewModel() {
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
    }

    //имитация получения лимитов из бэкэнда
    init {
        val monthLimit: List<Int> = listOf(3, 6, 9, 12, 24)
        val trackSlider = getTrackSlider(CREDIT_LIMIT_MAX, CREDIT_LIMIT_MIN, STEP_CREDIT)
        _initialCharacteristics.value =
            InitialCharacteristics(
                monthLimit = monthLimit,
                moneyLimit = trackSlider
            )
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

    fun renderLoansState(state: LoansState) {
        _stateLoans.value = state
    }

}