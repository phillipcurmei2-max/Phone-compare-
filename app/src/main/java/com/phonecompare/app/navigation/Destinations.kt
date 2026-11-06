package com.phonecompare.app.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations.
 *
 * Navigation Compose resolves these `@Serializable` types directly, avoiding
 * stringly-typed routes. Only [Home] exists in Milestone 1; comparison and
 * device-detail destinations are added in later milestones.
 */
@Serializable
data object Home
