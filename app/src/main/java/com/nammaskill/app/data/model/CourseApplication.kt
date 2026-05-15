package com.nammaskill.app.data.model

/**
 * Represents a course application submitted by a user.
 * Maps to the "applications" Firestore collection.
 */
data class CourseApplication(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val phone: String = "",
    val courseId: String = "",
    val courseTitle: String = "",
    val skillInterest: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
