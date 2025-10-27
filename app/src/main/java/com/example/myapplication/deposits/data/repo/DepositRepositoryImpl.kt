package com.example.myapplication.deposits.data.repo

import android.database.sqlite.SQLiteException
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.data.db.DepositDao
import com.example.myapplication.deposits.data.db.DepositEntity
import com.example.myapplication.deposits.data.mappers.toDomain
import com.example.myapplication.deposits.data.mock.DepositMock
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import kotlinx.coroutines.flow.Flow
import kotlinx.io.IOException

class DepositRepositoryImpl(
    private val client: NetworkClient,
    private val depositMock: DepositMock,
    private val depositDao: DepositDao
) : DepositsRepository {
    companion object {
        private const val SUCCESS_CODE = 200
        private const val NETWORK_ERROR = "Сетевая ошибка"
        private const val DB_ERROR = "Ошибка базы данных"
        private const val DATA_ERROR = "Ошибка данных"
    }

    override suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Result<Deposit> = try {
        val mockResponse = depositMock.getResponse()
        val response = client(mockResponse)

        if (response.code == SUCCESS_CODE) {
            val entity = DepositEntity(
                userId = userId,
                currentDepositNumber = currentDepositNumber,
                requestNumber = requestNumber,
                productId = 1L,
                type = "Standard",
                percentType = percentType,
                period = period,
                percent = 7,
                balance = 100_000L
            )
            depositDao.insertDeposit(entity)
            Result.Success(entity.toDomain())
        } else {
            Result.Error(response.description)
        }
    } catch (e: IOException) {
        formatError(NETWORK_ERROR, e)
    } catch (e: SQLiteException) {
        formatError(DB_ERROR, e)
    } catch (e: IllegalArgumentException) {
        formatError(DATA_ERROR, e)
    }

    override suspend fun closeDeposit(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Deposit> {
        return try {
            val mockResponse = depositMock.getResponse()
            val response = client(mockResponse)

            if (response.code == SUCCESS_CODE) {
                val deposit = depositDao.getDepositById(depositNumber)
                return if (deposit != null) {
                    depositDao.deleteDeposit(deposit)
                    Result.Success(deposit.toDomain())
                } else {
                    Result.Error("Вклад не найден")
                }
            } else {
                Result.Error(response.description)
            }
        } catch (e: IOException) {
            formatError(NETWORK_ERROR, e)
        } catch (e: SQLiteException) {
            formatError(DB_ERROR, e)
        } catch (e: IllegalArgumentException) {
            formatError(DATA_ERROR, e)
        }
    }

    override suspend fun getProducts(): Result<List<Deposit>> = try {
        val deposits = depositDao.getAllDeposits().map { it.toDomain() }
        Result.Success(deposits)
    } catch (e: IOException) {
        formatError(NETWORK_ERROR, e)
    } catch (e: SQLiteException) {
        formatError(DB_ERROR, e)
    } catch (e: IllegalArgumentException) {
        formatError(DATA_ERROR, e)
    }

    override suspend fun getDeposits(): List<Deposit> {
        return depositDao.getAllDeposits().map { it.toDomain() }
    }

    fun observeAllDeposits(): Flow<List<DepositEntity>> = depositDao.observeAll()

    private fun formatError(prefix: String, e: Exception): Result.Error {
        return Result.Error("$prefix: ${e.message}")
    }
}
