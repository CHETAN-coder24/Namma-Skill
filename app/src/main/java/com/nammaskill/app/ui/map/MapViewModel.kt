package com.nammaskill.app.ui.map

import androidx.lifecycle.ViewModel
import com.nammaskill.app.data.model.SkillCenter
import com.nammaskill.app.data.repository.SuccessStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: SuccessStoryRepository
) : ViewModel() {

    private val _skillCenters = MutableStateFlow<List<SkillCenter>>(emptyList())
    val skillCenters: StateFlow<List<SkillCenter>> = _skillCenters.asStateFlow()

    private val _selectedCenter = MutableStateFlow<SkillCenter?>(null)
    val selectedCenter: StateFlow<SkillCenter?> = _selectedCenter.asStateFlow()

    init {
        _skillCenters.value = repository.getSkillCenters()
    }

    fun selectCenter(center: SkillCenter?) {
        _selectedCenter.value = center
    }
}
