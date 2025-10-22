package com.example.myapplication.core.data.repo

import com.example.myapplication.core.data.db.AppDatabase
import com.example.myapplication.core.data.mappers.CardDbConverter
import com.example.myapplication.core.domain.api.CardRepository
import com.example.myapplication.core.domain.models.Card

class CardRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val cardDbConverter: CardDbConverter
) : CardRepository {
    override suspend fun add(card: Card) {
        appDatabase.cardDao().insertCard(cardDbConverter.map(card))
    }

    override suspend fun remove(card: Card) {
        appDatabase.cardDao().deleteCard(cardDbConverter.map(card))
    }

    override suspend fun getAll(): List<Card> {
        return appDatabase.cardDao().getAllCards().map { card ->
            cardDbConverter.map(card)
        }
    }

    override suspend fun getById(id: Long): Card? {
        val card = appDatabase.cardDao().getCardById(id)
        return if (card == null) null else cardDbConverter.map(card)
    }
}