package com.example.myapplication.core.domain.api

import com.example.myapplication.core.data.db.CardEntity

interface CardRepository {
    suspend fun add(card: CardEntity)
    suspend fun remove(card: CardEntity)
    suspend fun getAll(): List<CardEntity>
    suspend fun getById(id: Long): CardEntity?
}