package com.phonecompare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phonecompare.app.ui.home.HomeRoute

/**
 * Root navigation graph for PhoneCompare.
 *
 * Kept deliberately small for the foundation milestone: a single [Home]
 * destination. New destinations are registered here as features land.
 */
@Composable
fun PhoneCompareNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier,
    ) {
        composable<Home> {
            HomeRoute()
        }
    }
}
