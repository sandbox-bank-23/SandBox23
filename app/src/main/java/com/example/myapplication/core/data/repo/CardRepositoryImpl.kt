package com.example.myapplication.core.data.repo

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.mappers.CardDbConverter
import com.example.myapplication.core.domain.api.CardRepository
import com.example.myapplication.core.domain.models.Card

class CardRepositoryImpl(
    private val cardDao: CardDao,
    private val cardDbConverter: CardDbConverter
) : CardRepository {
    override suspend fun add(card: Card) {
        cardDao.insertCard(cardDbConverter.map(card))
    }

    override suspend fun remove(cardId: Long) {
        cardDao.deleteCard(cardId)
    }

    override suspend fun getAll(): List<Card> {
        return cardDao.getAllCards().map { card ->
            cardDbConverter.map(card)
        }
    }

    override suspend fun getById(id: Long): Card? {
        val card = cardDao.getCardById(id)
        return if (card == null) null else cardDbConverter.map(card)
    }
}