package com.example.myapplication.core.domain.api

import com.example.myapplication.core.domain.models.Card

interface CardRepository {
    suspend fun add(card: Card)
    suspend fun remove(cardId: Long)
    suspend fun getAll(): List<Card>
    suspend fun getById(id: Long): Card?
}