package com.phonecompare.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** Data-access object for the smartphone catalogue. */
@Dao
interface PhoneDao {

    @Query("SELECT * FROM phones ORDER BY brand, model")
    fun observeAll(): Flow<List<PhoneEntity>>

    @Query(
        """
        SELECT * FROM phones
        WHERE brand LIKE '%' || :query || '%'
           OR model LIKE '%' || :query || '%'
        ORDER BY brand, model
        """,
    )
    suspend fun search(query: String): List<PhoneEntity>

    @Query("SELECT COUNT(*) FROM phones")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM phones")
    fun observeCount(): Flow<Int>

    /** Bulk insert used by the catalogue importer in a later milestone. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(phones: List<PhoneEntity>)
}
