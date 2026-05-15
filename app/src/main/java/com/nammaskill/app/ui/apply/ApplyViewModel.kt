package com.nammaskill.app.ui.apply

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nammaskill.app.data.model.CourseApplication
import com.nammaskill.app.data.repository.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ApplyFormState(
    val name: String = "",
    val age: String = "",
    val phone: String = "",
    val skillInterest: String = "",
    val nameError: String? = null,
    val ageError: String? = null,
    val phoneError: String? = null
)

sealed class ApplyUiState {
    object Idle : ApplyUiState()
    object Submitting : ApplyUiState()
    data class Success(val applicationId: String) : ApplyUiState()
    data class Error(val message: String) : ApplyUiState()
}

@HiltViewModel
class ApplyViewModel @Inject constructor(
    private val applicationRepository: ApplicationRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(ApplyFormState())
    val formState: StateFlow<ApplyFormState> = _formState.asStateFlow()

    private val _uiState = MutableStateFlow<ApplyUiState>(ApplyUiState.Idle)
    val uiState: StateFlow<ApplyUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _formState.value = _formState.value.copy(name = name, nameError = null)
    }

    fun updateAge(age: String) {
        _formState.value = _formState.value.copy(age = age, ageError = null)
    }

    fun updatePhone(phone: String) {
        if (phone.length <= 10) {
            _formState.value = _formState.value.copy(phone = phone, phoneError = null)
        }
    }

    fun updateSkillInterest(interest: String) {
        _formState.value = _formState.value.copy(skillInterest = interest)
    }

    fun submitApplication(courseId: String, courseTitle: String) {
        val form = _formState.value

        // Validation
        var hasError = false
        var updatedForm = form

        if (form.name.isBlank()) {
            updatedForm = updatedForm.copy(nameError = "Name is required")
            hasError = true
        }

        val age = form.age.toIntOrNull()
        if (age == null || age < 14 || age > 60) {
            updatedForm = updatedForm.copy(ageError = "Enter a valid age (14-60)")
            hasError = true
        }

        if (form.phone.length != 10 || !form.phone.all { it.isDigit() }) {
            updatedForm = updatedForm.copy(phoneError = "Enter a valid 10-digit phone number")
            hasError = true
        }

        if (hasError) {
            _formState.value = updatedForm
            return
        }

        viewModelScope.launch {
            _uiState.value = ApplyUiState.Submitting

            val application = CourseApplication(
                name = form.name.trim(),
                age = age!!,
                phone = form.phone.trim(),
                courseId = courseId,
                courseTitle = courseTitle,
                skillInterest = form.skillInterest.ifBlank { courseTitle },
                timestamp = System.currentTimeMillis()
            )

            val result = applicationRepository.submitApplication(application)
            result.fold(
                onSuccess = { docId ->
                    _uiState.value = ApplyUiState.Success(docId)
                },
                onFailure = { error ->
                    _uiState.value = ApplyUiState.Error(
                        error.message ?: "Failed to submit application"
                    )
                }
            )
        }
    }
}
