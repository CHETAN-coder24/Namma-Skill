package com.nammaskill.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammaskill.app.data.model.Course
import com.nammaskill.app.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val courses: List<Course>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _allCourses = MutableStateFlow<List<Course>>(emptyList())

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedDuration = MutableStateFlow("All")
    val selectedDuration: StateFlow<String> = _selectedDuration.asStateFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Filtered courses derived from category + duration selections.
     */
    val filteredCourses: StateFlow<List<Course>> = combine(
        _allCourses,
        _selectedCategory,
        _selectedDuration
    ) { courses, category, duration ->
        courses.filter { course ->
            val matchesCategory = category == "All" || course.category == category
            val matchesDuration = when (duration) {
                "Short-term (≤3 mo)" -> course.durationMonths <= 3
                "Long-term (>3 mo)" -> course.durationMonths > 3
                else -> true
            }
            matchesCategory && matchesDuration
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            courseRepository.getCourses().collect { result ->
                result.fold(
                    onSuccess = { courses ->
                        _allCourses.value = courses
                        _uiState.value = HomeUiState.Success(courses)
                    },
                    onFailure = { error ->
                        _uiState.value = HomeUiState.Error(
                            error.message ?: "Failed to load courses"
                        )
                    }
                )
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectDuration(duration: String) {
        _selectedDuration.value = duration
    }
}
