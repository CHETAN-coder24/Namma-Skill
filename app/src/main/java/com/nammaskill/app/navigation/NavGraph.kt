package com.nammaskill.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nammaskill.app.ui.apply.ApplyScreen
import com.nammaskill.app.ui.details.CourseDetailScreen
import com.nammaskill.app.ui.home.HomeScreen
import com.nammaskill.app.ui.map.MapScreen
import com.nammaskill.app.ui.notifications.NotificationsScreen
import com.nammaskill.app.ui.stories.SuccessStoriesScreen

@Composable
fun NammaSkillNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Show bottom bar only on main screens
    val showBottomBar = currentDestination?.route in listOf(
        Screen.Home.route,
        Screen.Map.route,
        Screen.SuccessStories.route,
        Screen.Notifications.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }
        ) {
            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    onCourseClick = { courseId ->
                        navController.navigate(Screen.CourseDetail.createRoute(courseId))
                    }
                )
            }

            // Course Detail Screen
            composable(
                route = Screen.CourseDetail.route,
                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                CourseDetailScreen(
                    courseId = courseId,
                    onApplyClick = { id, title ->
                        navController.navigate(Screen.Apply.createRoute(id, title))
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Apply Screen
            composable(
                route = Screen.Apply.route,
                arguments = listOf(
                    navArgument("courseId") { type = NavType.StringType },
                    navArgument("courseTitle") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                val courseTitle = java.net.URLDecoder.decode(
                    backStackEntry.arguments?.getString("courseTitle") ?: "",
                    "UTF-8"
                )
                ApplyScreen(
                    courseId = courseId,
                    courseTitle = courseTitle,
                    onBackClick = { navController.popBackStack() },
                    onApplicationSubmitted = {
                        navController.popBackStack(Screen.Home.route, false)
                    }
                )
            }

            // Map Screen
            composable(Screen.Map.route) {
                MapScreen()
            }

            // Success Stories Screen
            composable(Screen.SuccessStories.route) {
                SuccessStoriesScreen()
            }

            // Notifications Screen
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
        }
    }
}
