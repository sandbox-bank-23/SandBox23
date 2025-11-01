@file:Suppress("MagicNumber")

package com.example.myapplication.profile.data.repo

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.dao.DepositDao
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.StorageKey
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
}