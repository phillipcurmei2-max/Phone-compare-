package com.phonecompare.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The app's offline-first Room database.
 *
 * `exportSchema` is disabled for the initial schema (version 1). It will be
 * enabled together with a `room.schemaLocation` KSP argument once migrations
 * are introduced in a later milestone.
 */
@Database(
    entities = [PhoneEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class PhoneCompareDatabase : RoomDatabase() {

    abstract fun phoneDao(): PhoneDao

    companion object {
        private const val DATABASE_NAME = "phonecompare.db"

        @Volatile
        private var instance: PhoneCompareDatabase? = null

        fun getInstance(context: Context): PhoneCompareDatabase =
            instance ?: synchronized(this) {
                instance ?: build(context).also { instance = it }
            }

        private fun build(context: Context): PhoneCompareDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                PhoneCompareDatabase::class.java,
                DATABASE_NAME,
            ).build()
    }
}
