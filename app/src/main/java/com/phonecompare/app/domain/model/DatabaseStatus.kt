package com.phonecompare.app.domain.model

/**
 * State of the on-device smartphone catalogue.
 *
 * PhoneCompare is offline-first: all device data lives in a local Room
 * database. Until that catalogue has been populated (a later milestone),
 * the app reports [Preparing] so the UI can be honest about the fact that
 * no real device data is available yet.
 */
sealed interface DatabaseStatus {

    /** The catalogue has not yet been populated with real device records. */
    data object Preparing : DatabaseStatus

    /** The catalogue contains [deviceCount] real device records. */
    data class Ready(val deviceCount: Int) : DatabaseStatus
}
