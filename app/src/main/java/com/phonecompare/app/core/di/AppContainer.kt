package com.phonecompare.app.core.di

import android.content.Context
import com.phonecompare.app.data.local.PhoneCompareDatabase
import com.phonecompare.app.data.repository.OfflinePhoneRepository
import com.phonecompare.app.domain.repository.PhoneRepository

/**
 * Lightweight manual dependency container.
 *
 * PhoneCompare deliberately uses manual DI for its foundation to avoid pulling
 * in a DI framework before it is warranted. The container exposes the
 * application-scoped singletons the UI layer depends on. It can be swapped for
 * Hilt/Koin later without touching call sites that depend only on the
 * [PhoneRepository] abstraction.
 */
interface AppContainer {
    val phoneRepository: PhoneRepository
}

/** Default container wiring the Room database to the offline repository. */
class DefaultAppContainer(context: Context) : AppContainer {

    private val applicationContext: Context = context.applicationContext

    private val database: PhoneCompareDatabase by lazy {
        PhoneCompareDatabase.getInstance(applicationContext)
    }

    override val phoneRepository: PhoneRepository by lazy {
        OfflinePhoneRepository(database.phoneDao())
    }
}
