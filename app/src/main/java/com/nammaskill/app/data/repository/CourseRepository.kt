package com.nammaskill.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammaskill.app.data.model.Course
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val coursesCollection = firestore.collection("courses")

    /**
     * Get all courses as a real-time Flow from Firestore.
     * Falls back to dummy data if Firestore is empty or unavailable.
     */
    fun getCourses(): Flow<Result<List<Course>>> = callbackFlow {
        val listener = coursesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Fallback to dummy data on error
                trySend(Result.success(getDummyCourses()))
                return@addSnapshotListener
            }

            val courses = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Course::class.java)?.copy(id = doc.id)
            } ?: emptyList()

            if (courses.isEmpty()) {
                // Seed dummy data and return it
                trySend(Result.success(getDummyCourses()))
                seedDummyData()
            } else {
                trySend(Result.success(courses))
            }
        }
        awaitClose { listener.remove() }
    }

    /**
     * Get a single course by ID.
     */
    fun getCourseById(courseId: String): Flow<Result<Course>> = flow {
        try {
            val doc = coursesCollection.document(courseId).get().await()
            val course = doc.toObject(Course::class.java)?.copy(id = doc.id)
            if (course != null) {
                emit(Result.success(course))
            } else {
                // Try to find in dummy data
                val dummy = getDummyCourses().find { it.id == courseId }
                if (dummy != null) {
                    emit(Result.success(dummy))
                } else {
                    emit(Result.failure(Exception("Course not found")))
                }
            }
        } catch (e: Exception) {
            // Fallback to dummy data
            val dummy = getDummyCourses().find { it.id == courseId }
            if (dummy != null) {
                emit(Result.success(dummy))
            } else {
                emit(Result.failure(e))
            }
        }
    }

    /**
     * Seed Firestore with dummy courses for demo purposes.
     */
    private fun seedDummyData() {
        val courses = getDummyCourses()
        courses.forEach { course ->
            coursesCollection.document(course.id).set(course)
        }
    }

    companion object {
        fun getDummyCourses(): List<Course> = listOf(
            Course(
                id = "course_001",
                title = "Advanced Electrician Training",
                category = "Electrician",
                duration = "3 months",
                durationMonths = 3,
                startDate = "June 1, 2026",
                location = "Bangalore, Karnataka",
                description = "Master residential and industrial electrical wiring, circuit design, safety protocols, and smart home installations. This government-certified course prepares you for immediate employment in the electrical industry.",
                eligibility = "10th Pass, Age 18-35, Basic understanding of mathematics",
                trainingCenter = "Karnataka Skill Development Centre",
                trainingCenterAddress = "Rajajinagar Industrial Area, Bangalore - 560010",
                jobGuarantee = true,
                batchSize = 30,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_002",
                title = "Industrial Sewing & Tailoring",
                category = "Sewing",
                duration = "2 months",
                durationMonths = 2,
                startDate = "June 15, 2026",
                location = "Mysore, Karnataka",
                description = "Learn professional garment construction, pattern making, machine operation, and fabric handling. Graduates can start their own tailoring business or join garment factories.",
                eligibility = "8th Pass, Age 16-40, No prior experience needed",
                trainingCenter = "Mysore Women's Skill Hub",
                trainingCenterAddress = "Devaraja Mohalla, Mysore - 570001",
                jobGuarantee = false,
                batchSize = 25,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_003",
                title = "Full-Stack Web Development",
                category = "Coding",
                duration = "6 months",
                durationMonths = 6,
                startDate = "July 1, 2026",
                location = "Hubli, Karnataka",
                description = "Comprehensive coding bootcamp covering HTML, CSS, JavaScript, React, Node.js, and databases. Build real-world projects and gain the skills needed for software development careers.",
                eligibility = "12th Pass, Age 18-30, Basic computer literacy",
                trainingCenter = "Digital India Skill Centre",
                trainingCenterAddress = "Vidyanagar, Hubli - 580021",
                jobGuarantee = true,
                batchSize = 40,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_004",
                title = "Mobile Phone Repair Technician",
                category = "Mobile Repair",
                duration = "1 month",
                durationMonths = 1,
                startDate = "June 10, 2026",
                location = "Mangalore, Karnataka",
                description = "Learn smartphone hardware and software repair, including screen replacement, battery service, motherboard troubleshooting, and software flashing. High demand skill in every town.",
                eligibility = "8th Pass, Age 16-35, Interest in electronics",
                trainingCenter = "Coastal Skill Foundation",
                trainingCenterAddress = "Hampankatta, Mangalore - 575001",
                jobGuarantee = true,
                batchSize = 20,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_005",
                title = "Home Electrician Certification",
                category = "Electrician",
                duration = "45 days",
                durationMonths = 2,
                startDate = "June 20, 2026",
                location = "Dharwad, Karnataka",
                description = "Short-term intensive course covering home wiring, switch installation, meter reading, earthing, and basic electrical safety. Perfect for quick job readiness.",
                eligibility = "8th Pass, Age 18-40",
                trainingCenter = "Dharwad ITI Extension Centre",
                trainingCenterAddress = "Saptapur, Dharwad - 580001",
                jobGuarantee = false,
                batchSize = 25,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_006",
                title = "Fashion Design & Embroidery",
                category = "Sewing",
                duration = "4 months",
                durationMonths = 4,
                startDate = "July 10, 2026",
                location = "Belgaum, Karnataka",
                description = "Advanced course covering fashion design principles, hand embroidery, machine embroidery, Kasuti work, and boutique management. Start your own fashion business.",
                eligibility = "10th Pass, Age 16-35, Creative interest",
                trainingCenter = "North Karnataka Fashion Institute",
                trainingCenterAddress = "Camp Area, Belgaum - 590001",
                jobGuarantee = true,
                batchSize = 20,
                fees = "₹500 (Subsidized)"
            ),
            Course(
                id = "course_007",
                title = "Android App Development",
                category = "Coding",
                duration = "5 months",
                durationMonths = 5,
                startDate = "August 1, 2026",
                location = "Bangalore, Karnataka",
                description = "Build Android apps using Kotlin and Jetpack Compose. Learn UI design, API integration, database management, and publish apps on Google Play Store.",
                eligibility = "12th Pass, Age 18-30, Basic computer skills",
                trainingCenter = "Bangalore Digital Academy",
                trainingCenterAddress = "Electronic City Phase 1, Bangalore - 560100",
                jobGuarantee = true,
                batchSize = 35,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_008",
                title = "Smartphone Hardware Repair",
                category = "Mobile Repair",
                duration = "2 months",
                durationMonths = 2,
                startDate = "June 25, 2026",
                location = "Shimoga, Karnataka",
                description = "Deep-dive into smartphone hardware: IC-level repairs, micro-soldering, data recovery, and component replacement. Advanced skills for running a repair shop.",
                eligibility = "10th Pass, Age 18-35, Basic soldering knowledge preferred",
                trainingCenter = "Malnad Technical Institute",
                trainingCenterAddress = "Gopala Gowda Extension, Shimoga - 577201",
                jobGuarantee = false,
                batchSize = 15,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_009",
                title = "Solar Panel Installation",
                category = "Electrician",
                duration = "2 months",
                durationMonths = 2,
                startDate = "July 5, 2026",
                location = "Gulbarga, Karnataka",
                description = "Learn solar panel installation, maintenance, inverter setup, and grid connection. Green energy skills with growing market demand across rural India.",
                eligibility = "10th Pass, Age 18-40, Basic electrical knowledge",
                trainingCenter = "Gulbarga Renewable Energy Centre",
                trainingCenterAddress = "Sedam Road, Gulbarga - 585105",
                jobGuarantee = true,
                batchSize = 25,
                fees = "Free (Government Sponsored)"
            ),
            Course(
                id = "course_010",
                title = "Data Entry & Office Skills",
                category = "Coding",
                duration = "1 month",
                durationMonths = 1,
                startDate = "June 5, 2026",
                location = "Davangere, Karnataka",
                description = "Master typing, MS Office, Google Workspace, data entry, and basic internet skills. Essential for office jobs, government clerical positions, and BPO roles.",
                eligibility = "10th Pass, Age 16-35",
                trainingCenter = "Davangere Computer Training Centre",
                trainingCenterAddress = "PJ Extension, Davangere - 577002",
                jobGuarantee = false,
                batchSize = 40,
                fees = "Free (Government Sponsored)"
            )
        )
    }
}
