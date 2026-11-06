package com.phonecompare.app

import android.app.Application
import com.phonecompare.app.core.di.AppContainer
import com.phonecompare.app.core.di.DefaultAppContainer

/**
 * Application entry point. Owns the [AppContainer] that provides
 * application-scoped dependencies (database, repositories) to the UI layer.
 */
class PhoneCompareApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
