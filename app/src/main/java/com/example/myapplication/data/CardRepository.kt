package com.example.myapplication.data

import com.example.myapplication.data.db.CardEntity

interface CardRepository {
    suspend fun add(card: CardEntity)
    suspend fun remove(card: CardEntity)
    suspend fun getAll(): List<CardEntity>
    suspend fun getById(id: Long): CardEntity?
}