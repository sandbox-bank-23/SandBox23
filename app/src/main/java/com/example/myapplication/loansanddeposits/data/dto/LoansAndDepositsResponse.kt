package com.example.myapplication.loansanddeposits.data.dto

import com.example.myapplication.core.domain.models.Product
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

@Serializable
data class LoansAndDepositsResponse(
    val code: Int,
    val description: String,
    @Serializable(with = StringToListSerializer::class)
    val response: List<Product>
)

object StringToListSerializer : KSerializer<List<Product>> {
    override val descriptor: SerialDescriptor =
        ListSerializer(Product.serializer()).descriptor

    override fun deserialize(decoder: Decoder): List<Product> {
        return Json.decodeFromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: List<Product>) {
        encoder.encodeString(Json.encodeToString(value))
    }
}