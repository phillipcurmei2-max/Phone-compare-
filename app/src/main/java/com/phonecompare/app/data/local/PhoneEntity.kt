package com.phonecompare.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room persistence model for a smartphone record.
 *
 * Screen dimensions are stored in millimetres (physical) and pixels
 * (resolution) so the catalogue can drive mathematically accurate screen
 * outlines. This table is created empty; real records are imported in a
 * later milestone.
 */
@Entity(tableName = "phones")
data class PhoneEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "model")
    val model: String,

    @ColumnInfo(name = "release_year")
    val releaseYear: Int?,

    @ColumnInfo(name = "physical_width_mm")
    val physicalWidthMm: Double,

    @ColumnInfo(name = "physical_height_mm")
    val physicalHeightMm: Double,

    @ColumnInfo(name = "diagonal_inches")
    val diagonalInches: Double,

    @ColumnInfo(name = "resolution_width_px")
    val resolutionWidthPx: Int,

    @ColumnInfo(name = "resolution_height_px")
    val resolutionHeightPx: Int,

    @ColumnInfo(name = "pixel_density_ppi")
    val pixelDensityPpi: Int,
)
