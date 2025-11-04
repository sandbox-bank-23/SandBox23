@file:Suppress("MagicNumber")

package com.example.myapplication.profile.data.repo

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.dao.DepositDao
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.profile.domain.UserData
import com.example.myapplication.profile.domain.api.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import kotlin.random.Random

class ProfileRepositoryImpl(
    private val appStorage: AppStorage,
    private val depositDao: DepositDao,
    private val loanDao: LoanDao,
    private val userDao: UserDao,
    private val cardDao: CardDao
) : ProfileRepository {

    @Suppress("MaxLineLength")
    override suspend fun isLatestVersion(): Flow<Response> = flowOf(
        Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(Random.nextInt(1, 100) <= 95)
        )
    )

    override fun getTheme(): Flow<Boolean> =
        appStorage.getBooleanValue(StorageKey.THEME)

    override suspend fun changeTheme() {
        val current = appStorage.getBooleanValue(StorageKey.THEME).first()
        appStorage.saveBooleanValue(StorageKey.THEME, !current)
    }

    override suspend fun clearAppData() {
        appStorage.clearAll()
        depositDao.deleteAll()
        cardDao.deleteAll()
        loanDao.deleteAll()
        userDao.deleteAll()
    }

    override suspend fun getUserData(): UserData {
        val user = userDao.getAllUsers().first()

        val userId = user.id

        val allCards = cardDao.getAllCards().filter { it.userId == userId }

        val creditCards = allCards.filter { it.type.equals("CREDIT", ignoreCase = true) }

        val debitCards = allCards.filter { !it.type.equals("CREDIT", ignoreCase = true) }

        val deposits = depositDao.getAllDeposits().filter { it.userId == userId }

        val loans = loanDao.getUserWithLoans(userId)?.loans ?: emptyList()

        val creditCardsBalance = creditCards.sumOf { it.balance }
        val debitCardsBalance = debitCards.sumOf { it.balance }
        val depositsBalance = deposits.sumOf { it.balance }
        val loansBalance = loans.sumOf { it.balance.toLong() }

        val totalBalance = debitCardsBalance + depositsBalance - (creditCardsBalance + loansBalance)

        return UserData(
            name = user.firstName + " " + user.lastName,
            id = user.id.toString(),
            totalBalance = totalBalance,
            creditCardsBalance = creditCardsBalance,
            depositsBalance = depositsBalance,
            loansBalance = loansBalance
        )
    }
}