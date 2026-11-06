package com.phonecompare.app.data.mapper

import com.phonecompare.app.data.local.PhoneEntity
import com.phonecompare.app.domain.model.PhoneSpec
import com.phonecompare.app.domain.model.Resolution
import com.phonecompare.app.domain.model.ScreenSpec

/** Maps a persistence [PhoneEntity] to its immutable [PhoneSpec] domain model. */
fun PhoneEntity.toDomain(): PhoneSpec = PhoneSpec(
    id = id,
    brand = brand,
    model = model,
    releaseYear = releaseYear,
    screen = ScreenSpec(
        physicalWidthMm = physicalWidthMm,
        physicalHeightMm = physicalHeightMm,
        diagonalInches = diagonalInches,
        resolution = Resolution(resolutionWidthPx, resolutionHeightPx),
        pixelDensityPpi = pixelDensityPpi,
    ),
)

/** Maps a [PhoneSpec] domain model to its persistence [PhoneEntity]. */
fun PhoneSpec.toEntity(): PhoneEntity = PhoneEntity(
    id = id,
    brand = brand,
    model = model,
    releaseYear = releaseYear,
    physicalWidthMm = screen.physicalWidthMm,
    physicalHeightMm = screen.physicalHeightMm,
    diagonalInches = screen.diagonalInches,
    resolutionWidthPx = screen.resolution.widthPx,
    resolutionHeightPx = screen.resolution.heightPx,
    pixelDensityPpi = screen.pixelDensityPpi,
)
