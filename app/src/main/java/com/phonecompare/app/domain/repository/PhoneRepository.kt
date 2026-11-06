package com.phonecompare.app.domain.repository

import com.phonecompare.app.domain.model.DatabaseStatus
import com.phonecompare.app.domain.model.PhoneSpec
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction over the smartphone catalogue.
 *
 * The domain layer depends only on this interface; the concrete, offline-first
 * Room-backed implementation lives in the data layer. No implementation may
 * fabricate device records — results come solely from the local database.
 */
interface PhoneRepository {

    /** Observe all catalogued devices, ordered by brand then model. */
    fun observePhones(): Flow<List<PhoneSpec>>

    /**
     * Search the catalogue by brand or model.
     *
     * Returns an empty list for a blank query or while the catalogue is empty.
     */
    suspend fun search(query: String): List<PhoneSpec>

    /** The number of real device records currently stored. */
    suspend fun getDeviceCount(): Int

    /** Observe the catalogue's readiness so the UI can reflect it reactively. */
    fun observeDatabaseStatus(): Flow<DatabaseStatus>
}
