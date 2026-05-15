package com.nammaskill.app.ui.details

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammaskill.app.NammaSkillApp
import com.nammaskill.app.data.model.Course
import com.nammaskill.app.data.repository.ApplicationRepository
import com.nammaskill.app.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val course: Course) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val applicationRepository: ApplicationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _interestSent = MutableStateFlow(false)
    val interestSent: StateFlow<Boolean> = _interestSent.asStateFlow()

    private val _interestLoading = MutableStateFlow(false)
    val interestLoading: StateFlow<Boolean> = _interestLoading.asStateFlow()

    fun loadCourse(courseId: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            courseRepository.getCourseById(courseId).collect { result ->
                result.fold(
                    onSuccess = { course ->
                        _uiState.value = DetailUiState.Success(course)
                    },
                    onFailure = { error ->
                        _uiState.value = DetailUiState.Error(
                            error.message ?: "Failed to load course details"
                        )
                    }
                )
            }
        }
    }

    fun markInterested(courseId: String, courseTitle: String) {
        viewModelScope.launch {
            _interestLoading.value = true
            val result = applicationRepository.recordInterest(courseId, courseTitle)
            result.fold(
                onSuccess = {
                    _interestSent.value = true
                    showInterestNotification(courseTitle)
                },
                onFailure = {
                    // Still mark as sent for demo purposes
                    _interestSent.value = true
                    showInterestNotification(courseTitle)
                }
            )
            _interestLoading.value = false
        }
    }

    private fun showInterestNotification(courseTitle: String) {
        try {
            val notification = NotificationCompat.Builder(context, NammaSkillApp.INTEREST_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Interest Recorded! 🎯")
                .setContentText("A trainer from \"$courseTitle\" will call you back soon.")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Your interest in \"$courseTitle\" has been recorded. A trainer will contact you within 24 hours to discuss the course details and enrollment process.")
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(courseTitle.hashCode(), notification)
        } catch (e: Exception) {
            // Notification permission might not be granted — gracefully ignore
        }
    }
}
