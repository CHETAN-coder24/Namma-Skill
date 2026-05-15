package com.nammaskill.app.ui.stories

import androidx.lifecycle.ViewModel
import com.nammaskill.app.data.model.SuccessStory
import com.nammaskill.app.data.repository.SuccessStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SuccessStoriesViewModel @Inject constructor(
    repository: SuccessStoryRepository
) : ViewModel() {

    private val _stories = MutableStateFlow<List<SuccessStory>>(emptyList())
    val stories: StateFlow<List<SuccessStory>> = _stories.asStateFlow()

    init {
        _stories.value = repository.getSuccessStories()
    }
}
