@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.transfer.data.mock

import com.example.myapplication.core.domain.models.Response
import kotlin.random.Random

class TransferMock {
    fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> transfer()
            in 81..85 -> invalidTransactionNumber()
            in 86..90 -> cantTransfer()
            in 91..95 -> invalidOrExpiredToken()
            in 96..100 -> transactionCompleted()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    fun transfer(): Response = Response(
        code = 200,
        description = "OK",
        response = "\"transaction_number: ${Random.nextLong(1, 999999)}\""
    )

    fun invalidTransactionNumber(): Response = Response(
        code = 400,
        description = "Invalid transaction number",
        response = null
    )

    fun cantTransfer(): Response = Response(
        code = 400,
        description = "Can not transfer",
        response = null
    )

    fun invalidOrExpiredToken(): Response = Response(
        code = 403,
        description = "Token is invalid or expired",
        response = null
    )

    fun transactionCompleted(): Response = Response(
        code = 409,
        description = "This transaction already completed",
        response = null
    )
}