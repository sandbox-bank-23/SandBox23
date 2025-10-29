package com.example.myapplication.cards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.cards.domain.usecases.GetCardsUseCase
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CardsViewModel(
    val getCardsUseCase: GetCardsUseCase,
    val appInteractor: AppInteractor
) : ViewModel() {
    private val _cardsState = MutableStateFlow<CardsState>(value = CardsState.Empty)
    val cardsState: StateFlow<CardsState> = _cardsState.asStateFlow()

    init {
        loadCards()
    }

    fun loadCards() {
        viewModelScope.launch {
            val userId = appInteractor.getAuthDataValue(StorageKey.AUTHDATA).firstOrNull()?.userId
            val userIdFormat = userId?.toLongOrNull() ?: -1L
            when (val result = getCardsUseCase(userIdFormat)) {
                is Result.Success -> {
                    val cards = result.data
                    if (cards.isEmpty()) {
                        renderState(CardsState.Empty)
                    } else {
                        renderState(CardsState.Cards(cards))
                    }
                }
                is Result.Error -> {
                    renderState(CardsState.Empty)
                }
            }
        }
    }

    private fun renderState(state: CardsState) {
        _cardsState.value = state
    }
}