package com.nammaskill.app.data.model

/**
 * Represents a success story from a past trainee.
 */
data class SuccessStory(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val courseCompleted: String = "",
    val jobAchieved: String = "",
    val testimonial: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val completionYear: Int = 2024
)
