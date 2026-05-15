package com.nammaskill.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed class defining all navigation destinations in the app.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CourseDetail : Screen("course/{courseId}") {
        fun createRoute(courseId: String) = "course/$courseId"
    }
    object Apply : Screen("apply/{courseId}/{courseTitle}") {
        fun createRoute(courseId: String, courseTitle: String) =
            "apply/$courseId/${java.net.URLEncoder.encode(courseTitle, "UTF-8")}"
    }
    object Map : Screen("map")
    object SuccessStories : Screen("stories")
    object Notifications : Screen("notifications")
}

/**
 * Bottom navigation items.
 */
data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        label = "Home",
        route = Screen.Home.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        label = "Map",
        route = Screen.Map.route,
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map
    ),
    BottomNavItem(
        label = "Stories",
        route = Screen.SuccessStories.route,
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.StarBorder
    ),
    BottomNavItem(
        label = "Alerts",
        route = Screen.Notifications.route,
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications
    )
)
