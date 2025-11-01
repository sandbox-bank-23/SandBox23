package com.example.myapplication.deposits.data.repo

import android.database.sqlite.SQLiteException
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.deposits.data.db.DepositDao
import com.example.myapplication.deposits.data.db.DepositEntity
import com.example.myapplication.deposits.data.mappers.toDomain
import com.example.myapplication.deposits.data.mock.DepositMock
import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class DepositRepositoryImpl(
    private val client: NetworkClient,
    private val depositMock: DepositMock,
    private val depositDao: DepositDao
) : DepositsRepository {
    companion object {
        private const val SUCCESS_CODE = 200
        private const val CREATED_CODE = 201
    }

    @Suppress("LongMethod", "CyclomaticComplexMethod")
    override suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): DepositResult<Deposit> = try {
        val mockResponse = depositMock.getResponse()
        val response = client(mockResponse)

        if (response.code == SUCCESS_CODE || response.code == CREATED_CODE) {
            val productJson = try {
                val root = Json.parseToJsonElement(response.response ?: "")
                val dataObj = root.jsonObject["data"]?.jsonObject
                    ?: return DepositResult.NetworkError("Missing data in server response")
                val innerResponse = dataObj["response"]?.jsonPrimitive?.content
                    ?: return DepositResult.NetworkError("Missing product JSON in server response")
                innerResponse
            } catch (e: SerializationException) {
                return DepositResult.DataBaseError(e.message)
            }

            val product = try {
                Json.decodeFromString<Product>(productJson)
            } catch (e: SerializationException) {
                return DepositResult.DataBaseError(e.message)
            }

            val entity = DepositEntity(
                userId = userId,
                currentDepositNumber = currentDepositNumber,
                requestNumber = requestNumber,
                productId = product.id,
                type = product.type,
                percentType = product.percentType,
                period = product.period,
                percent = product.percent.toInt(),
                balance = product.balance
            )

            depositDao.insertDeposit(entity)

            DepositResult.Success(entity.toDomain())

        } else {
            DepositResult.NetworkError(response.description)
        }

    } catch (e: IOException) {
        DepositResult.NetworkError(e.message)
    } catch (e: SQLiteException) {
        DepositResult.DataBaseError(e.message)
    } catch (e: IllegalArgumentException) {
        DepositResult.InputError(e.message)
    }

    override suspend fun closeDeposit(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long
    ): DepositResult<Deposit> {
        return try {
            val mockResponse = depositMock.getResponse()
            val response = client(mockResponse)

            if (response.code == SUCCESS_CODE) {
                val deposit = depositDao.getDepositById(depositNumber)
                return if (deposit != null) {
                    depositDao.deleteDeposit(deposit)
                    DepositResult.Success(deposit.toDomain())
                } else {
                    DepositResult.Empty("")
                }
            } else {
                DepositResult.NetworkError("")
            }
        } catch (e: IOException) {
            DepositResult.NetworkError(e.message)
        } catch (e: SQLiteException) {
            DepositResult.DataBaseError(e.message)
        } catch (e: IllegalArgumentException) {
            DepositResult.InputError(e.message)
        }
    }

    override suspend fun takeDeposit(id: Long): DepositResult<Deposit> {
        return try {
            val deposit = depositDao.getDepositById(id)
            if (deposit != null) {
                DepositResult.Success(deposit.toDomain())
            } else {
                DepositResult.NetworkError("Депозит не найден")
            }
        } catch (e: IOException) {
            DepositResult.NetworkError(e.message)
        } catch (e: SQLiteException) {
            DepositResult.DataBaseError(e.message)
        } catch (e: IllegalArgumentException) {
            DepositResult.InputError(e.message)
        }
    }


    override suspend fun getProducts(): DepositResult<List<Deposit>> = try {
        val deposits = depositDao.getAllDeposits().map { it.toDomain() }
        DepositResult.Success(deposits)
    } catch (e: IOException) {
        DepositResult.NetworkError(e.message)
    } catch (e: SQLiteException) {
        DepositResult.DataBaseError(e.message)
    } catch (e: IllegalArgumentException) {
        DepositResult.InputError(e.message)
    }

    override suspend fun getDeposits(): List<Deposit> {
        return depositDao.getAllDeposits().map { it.toDomain() }
    }

    override suspend fun observeAllDeposits(): Flow<List<Deposit>> {
        val result = depositDao.observeAll().map { entityList ->
            entityList.map { it.toDomain() }
        }
        return result
    }
}
