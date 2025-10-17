package com.example.myapplication.cards.domain.usecases

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.Card

class GetCardsUseCase(private val getCardsRepository: CardsRepository) {
    suspend fun getCards(userId: Long): Result<List<Card>> = getCardsRepository.getCards(userId)
}