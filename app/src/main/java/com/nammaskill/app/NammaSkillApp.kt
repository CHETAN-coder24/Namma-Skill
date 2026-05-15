package com.nammaskill.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NammaSkillApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Namma Skill Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for new course batches and updates"
                enableLights(true)
                enableVibration(true)
            }

            val interestChannel = NotificationChannel(
                INTEREST_CHANNEL_ID,
                "Interest Confirmations",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Confirmation when you express interest in a course"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(interestChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "namma_skill_channel"
        const val INTEREST_CHANNEL_ID = "namma_skill_interest"
    }
}
