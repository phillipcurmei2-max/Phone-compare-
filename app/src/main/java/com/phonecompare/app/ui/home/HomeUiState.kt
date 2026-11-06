package com.phonecompare.app.ui.home

import androidx.compose.runtime.Immutable
import com.phonecompare.app.domain.model.DatabaseStatus

/**
 * Immutable state for the landing screen.
 *
 * All screen state is represented by this single value so the UI is a pure
 * function of state. [databaseStatus] drives honest messaging about whether
 * real device data is available yet.
 */
@Immutable
data class HomeUiState(
    val query: String = "",
    val databaseStatus: DatabaseStatus = DatabaseStatus.Preparing,
) {
    /** Real device records currently available (0 while preparing). */
    val deviceCount: Int
        get() = (databaseStatus as? DatabaseStatus.Ready)?.deviceCount ?: 0

    /** Whether the catalogue has real data the user can search. */
    val isCatalogueReady: Boolean
        get() = databaseStatus is DatabaseStatus.Ready
}
