package com.phonecompare.app.data.repository

import com.phonecompare.app.data.local.PhoneDao
import com.phonecompare.app.data.mapper.toDomain
import com.phonecompare.app.domain.model.DatabaseStatus
import com.phonecompare.app.domain.model.PhoneSpec
import com.phonecompare.app.domain.repository.PhoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Offline-first [PhoneRepository] backed entirely by the local Room database.
 *
 * It never fabricates data: an empty catalogue yields empty results and a
 * [DatabaseStatus.Preparing] status.
 */
class OfflinePhoneRepository(
    private val phoneDao: PhoneDao,
) : PhoneRepository {

    override fun observePhones(): Flow<List<PhoneSpec>> =
        phoneDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun search(query: String): List<PhoneSpec> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return emptyList()
        return phoneDao.search(trimmed).map { it.toDomain() }
    }

    override suspend fun getDeviceCount(): Int = phoneDao.count()

    override fun observeDatabaseStatus(): Flow<DatabaseStatus> =
        phoneDao.observeCount().map { count ->
            if (count <= 0) DatabaseStatus.Preparing else DatabaseStatus.Ready(count)
        }
}
