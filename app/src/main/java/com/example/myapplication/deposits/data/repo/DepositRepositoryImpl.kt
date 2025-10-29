package com.example.myapplication.deposits.data.repo

import android.database.sqlite.SQLiteException
import com.example.myapplication.core.data.network.NetworkClient
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

class DepositRepositoryImpl(
    private val client: NetworkClient,
    private val depositMock: DepositMock,
    private val depositDao: DepositDao
) : DepositsRepository {
    companion object {
        private const val SUCCESS_CODE = 200
        private const val CREATED_CODE = 201
    }

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
            DepositResult.Success(entity.toDomain())
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
            return if (deposit != null) {
                DepositResult.Success(deposit.toDomain())
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
