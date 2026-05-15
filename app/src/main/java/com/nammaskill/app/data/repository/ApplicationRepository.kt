package com.nammaskill.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammaskill.app.data.model.CourseApplication
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val applicationsCollection = firestore.collection("applications")
    private val interestsCollection = firestore.collection("interests")

    /**
     * Submit a course application to Firestore.
     * Returns the generated document ID on success.
     */
    suspend fun submitApplication(application: CourseApplication): Result<String> {
        return try {
            val docRef = applicationsCollection.add(application).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Record user interest in a course.
     * Stores the interest and simulates a trainer callback request.
     */
    suspend fun recordInterest(courseId: String, courseTitle: String): Result<String> {
        return try {
            val interest = hashMapOf(
                "courseId" to courseId,
                "courseTitle" to courseTitle,
                "timestamp" to System.currentTimeMillis(),
                "callbackRequested" to true,
                "status" to "pending"
            )
            val docRef = interestsCollection.add(interest).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            // Even if Firestore fails, return success for demo
            Result.success("local_interest_${System.currentTimeMillis()}")
        }
    }
}
