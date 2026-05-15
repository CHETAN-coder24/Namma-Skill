package com.nammaskill.app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nammaskill.app.ui.components.CategoryFilterChips
import com.nammaskill.app.ui.components.CourseCard
import com.nammaskill.app.ui.components.DurationFilterChips
import com.nammaskill.app.ui.components.EmptyState
import com.nammaskill.app.ui.components.ErrorState
import com.nammaskill.app.ui.components.LoadingIndicator
import com.nammaskill.app.ui.theme.GradientEnd
import com.nammaskill.app.ui.theme.GradientStart
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onCourseClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredCourses by viewModel.filteredCourses.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedDuration by viewModel.selectedDuration.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Hero Header
        HomeHeader()

        when (uiState) {
            is HomeUiState.Loading -> {
                LoadingIndicator(message = "Finding courses near you...")
            }

            is HomeUiState.Error -> {
                ErrorState(
                    message = (uiState as HomeUiState.Error).message,
                    onRetry = { viewModel.loadCourses() }
                )
            }

            is HomeUiState.Success -> {
                // Filters
                Spacer(modifier = Modifier.height(8.dp))
                CategoryFilterChips(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { viewModel.selectCategory(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DurationFilterChips(
                    selectedDuration = selectedDuration,
                    onDurationSelected = { viewModel.selectDuration(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Course count
                Text(
                    text = "${filteredCourses.size} course${if (filteredCourses.size != 1) "s" else ""} available",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (filteredCourses.isEmpty()) {
                    EmptyState()
                } else {
                    // Course List with staggered animations
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(
                            items = filteredCourses,
                            key = { _, course -> course.id }
                        ) { index, course ->
                            var visible by remember { mutableStateOf(false) }
                            LaunchedEffect(course.id) {
                                delay(index * 50L)
                                visible = true
                            }

                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn() + slideInVertically(
                                    initialOffsetY = { it / 2 }
                                )
                            ) {
                                CourseCard(
                                    course = course,
                                    onClick = { onCourseClick(course.id) }
                                )
                            }
                        }

                        // Bottom spacing
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = null,
                tint = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    text = "Namma Skill",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
                Text(
                    text = "Discover skill courses near you",
                    style = MaterialTheme.typography.bodySmall,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}
