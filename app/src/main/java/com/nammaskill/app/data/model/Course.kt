package com.nammaskill.app.data.model

/**
 * Represents a vocational training course.
 * Maps to the "courses" Firestore collection.
 */
data class Course(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val duration: String = "",
    val durationMonths: Int = 0,
    val startDate: String = "",
    val location: String = "",
    val description: String = "",
    val eligibility: String = "",
    val trainingCenter: String = "",
    val trainingCenterAddress: String = "",
    val jobGuarantee: Boolean = false,
    val imageUrl: String = "",
    val batchSize: Int = 30,
    val fees: String = "Free (Government Sponsored)"
)
