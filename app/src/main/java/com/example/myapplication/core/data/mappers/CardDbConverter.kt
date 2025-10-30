package com.example.myapplication.core.data.mappers

import com.example.myapplication.core.data.db.CardEntity
import com.example.myapplication.core.domain.models.Card

class CardDbConverter {

    fun map(card: Card): CardEntity {
        return CardEntity(
            id = card.id,
            cvv = card.cvv,
            endDate = card.endDate,
            owner = card.owner,
            userId = card.userId,
            type = card.type,
            percent = card.percent,
            balance = card.balance
        )
    }

    fun map(card: CardEntity): Card {
        return Card(
            id = card.id,
            cvv = card.cvv,
            endDate = card.endDate,
            owner = card.owner,
            userId = card.userId,
            type = card.type,
            percent = card.percent,
            balance = card.balance ?: 0L
        )
    }
}