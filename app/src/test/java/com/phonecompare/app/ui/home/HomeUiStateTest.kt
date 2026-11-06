package com.phonecompare.app.ui.home

import com.phonecompare.app.domain.model.DatabaseStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeUiStateTest {

    @Test
    fun `preparing status reports no devices and not ready`() {
        val state = HomeUiState(databaseStatus = DatabaseStatus.Preparing)
        assertEquals(0, state.deviceCount)
        assertFalse(state.isCatalogueReady)
    }

    @Test
    fun `ready status exposes device count and readiness`() {
        val state = HomeUiState(databaseStatus = DatabaseStatus.Ready(deviceCount = 10234))
        assertEquals(10234, state.deviceCount)
        assertTrue(state.isCatalogueReady)
    }
}
