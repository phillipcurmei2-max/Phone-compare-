package com.phonecompare.app.domain.model

import kotlin.math.roundToInt

/**
 * Immutable domain representation of a smartphone and the physical
 * characteristics PhoneCompare needs to draw an accurately scaled screen.
 *
 * Physical screen dimensions are expressed in millimetres so the comparison
 * engine (a later milestone) can render outlines at true relative physical
 * size regardless of the viewing device.
 *
 * This type intentionally carries no sample/placeholder instances — real
 * device records are only ever loaded from the local database.
 */
data class PhoneSpec(
    val id: String,
    val brand: String,
    val model: String,
    val releaseYear: Int?,
    val screen: ScreenSpec,
) {
    val displayName: String get() = "$brand $model"
}

/**
 * Physical and pixel characteristics of a device's screen.
 *
 * @property physicalWidthMm   active screen width in millimetres
 * @property physicalHeightMm  active screen height in millimetres
 * @property diagonalInches    advertised diagonal in inches
 * @property resolution        native pixel resolution
 * @property pixelDensityPpi   pixel density in pixels-per-inch
 */
data class ScreenSpec(
    val physicalWidthMm: Double,
    val physicalHeightMm: Double,
    val diagonalInches: Double,
    val resolution: Resolution,
    val pixelDensityPpi: Int,
) {
    /** Reduced aspect ratio derived from the native resolution. */
    val aspectRatio: AspectRatio get() = AspectRatio.fromResolution(resolution)
}

/** Native pixel resolution of a screen. */
data class Resolution(
    val widthPx: Int,
    val heightPx: Int,
) {
    val totalPixels: Long get() = widthPx.toLong() * heightPx.toLong()

    override fun toString(): String = "$widthPx x $heightPx"
}

/**
 * A screen aspect ratio reduced to its smallest integer terms (e.g. 19.5 : 9).
 * This is a simple geometric reduction of the resolution — not the screen
 * comparison engine, which is a later milestone.
 */
data class AspectRatio(
    val horizontal: Double,
    val vertical: Double,
) {
    override fun toString(): String {
        fun fmt(v: Double) = if (v % 1.0 == 0.0) v.roundToInt().toString() else ((v * 10).roundToInt() / 10.0).toString()
        return "${fmt(horizontal)} : ${fmt(vertical)}"
    }

    companion object {
        fun fromResolution(resolution: Resolution): AspectRatio {
            val w = resolution.widthPx
            val h = resolution.heightPx
            if (w <= 0 || h <= 0) return AspectRatio(0.0, 0.0)
            val divisor = gcd(w, h)
            // Normalise so the longer edge is expressed against 9 where sensible;
            // otherwise return the plain reduced ratio.
            val longer = maxOf(w, h).toDouble() / divisor
            val shorter = minOf(w, h).toDouble() / divisor
            return AspectRatio(
                horizontal = (longer / shorter * 9).let { (it * 10).roundToInt() / 10.0 },
                vertical = 9.0,
            )
        }

        private fun gcd(a: Int, b: Int): Int {
            var x = a
            var y = b
            while (y != 0) {
                val t = y
                y = x % y
                x = t
            }
            return x
        }
    }
}
