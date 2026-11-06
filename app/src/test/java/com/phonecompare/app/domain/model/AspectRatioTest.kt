package com.phonecompare.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AspectRatioTest {

    @Test
    fun `20_9 resolution reduces to a 9-based ratio`() {
        // 1080 x 2400 is a common 20:9 panel.
        val ratio = AspectRatio.fromResolution(Resolution(1080, 2400))
        assertEquals(20.0, ratio.horizontal, 0.05)
        assertEquals(9.0, ratio.vertical, 0.0)
    }

    @Test
    fun `16_9 resolution reduces correctly`() {
        val ratio = AspectRatio.fromResolution(Resolution(1080, 1920))
        assertEquals(16.0, ratio.horizontal, 0.05)
        assertEquals(9.0, ratio.vertical, 0.0)
    }

    @Test
    fun `zero dimensions are handled safely`() {
        val ratio = AspectRatio.fromResolution(Resolution(0, 0))
        assertEquals(0.0, ratio.horizontal, 0.0)
        assertEquals(0.0, ratio.vertical, 0.0)
    }
}
