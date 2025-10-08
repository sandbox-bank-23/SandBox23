package com.example.myapplication.core.data.repo

import com.example.myapplication.core.domain.api.CardRepository
import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.CardEntity

class CardRepositoryImpl (private val dao: CardDao) : CardRepository {
    override suspend fun add(card: CardEntity) {
        dao.insertCard(card)
    }

    override suspend fun remove(card: CardEntity) {
        dao.deleteCard(card)
    }

    override suspend fun getAll(): List<CardEntity> {
        return dao.getAllCards()
    }

    override suspend fun getById(id: Long): CardEntity? {
        return dao.getCardById(id)
    }
}