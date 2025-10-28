    package com.example.myapplication.cards.domain.usecases

    import com.example.myapplication.cards.domain.api.CardsRepository
    import com.example.myapplication.core.domain.models.Card
    import com.example.myapplication.core.domain.models.Result

    class GetCardsUseCase(private val getCardsRepository: CardsRepository) {
        suspend operator fun invoke(userId: Long): Result<List<Card>> = getCardsRepository.getCards(userId)
    }