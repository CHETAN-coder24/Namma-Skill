package com.nammaskill.app.data.model

/**
 * Represents a skill development center location for the map.
 */
data class SkillCenter(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val phone: String = "",
    val coursesOffered: List<String> = emptyList(),
    val rating: Float = 4.0f
)
