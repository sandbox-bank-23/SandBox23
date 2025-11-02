package com.example.myapplication.creditcards.data.repo.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

@Serializable
data class CreditCardMock(
    val data: CreditCardMockResponse
)

@Serializable
data class CreditCardMockResponse(
    val code: Int,
    val description: String,
    @Serializable(with = ResponseCreditDataSerializer::class)
    val response: ResponseData
)

object ResponseCreditDataSerializer : KSerializer<ResponseData> {
    override val descriptor: SerialDescriptor = ResponseData.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ResponseData) {
        ResponseData.serializer().serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): ResponseData {
        val jsonString = decoder.decodeString()
        return Json.decodeFromString<ResponseData>(jsonString)
    }
}