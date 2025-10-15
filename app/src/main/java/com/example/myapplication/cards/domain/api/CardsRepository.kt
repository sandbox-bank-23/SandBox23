package com.example.myapplication.cards.domain.api

import com.example.myapplication.core.data.network.Response

interface CardsRepository {
    suspend fun getCards(): Response
}