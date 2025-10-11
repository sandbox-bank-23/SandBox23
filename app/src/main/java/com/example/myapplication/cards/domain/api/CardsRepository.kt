package com.example.myapplication.cards.domain.api

import com.example.myapplication.core.domain.models.Response

interface CardsRepository {
    suspend fun getCards(): Response
}