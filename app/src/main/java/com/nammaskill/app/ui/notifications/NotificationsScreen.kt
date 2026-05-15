package com.nammaskill.app.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammaskill.app.ui.theme.GradientEnd
import com.nammaskill.app.ui.theme.GradientStart

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isNew: Boolean = false
)

private val sampleNotifications = listOf(
    NotificationItem("1", "New Batch Starting! 🎉", "Advanced Electrician Training batch starts on June 1. Apply now!", "2 hours ago", true),
    NotificationItem("2", "Application Received ✅", "Your application for Full-Stack Web Development has been received.", "Yesterday", true),
    NotificationItem("3", "New Course Added", "Solar Panel Installation course now available in Gulbarga.", "2 days ago", false),
    NotificationItem("4", "Trainer Callback", "A trainer from Mobile Phone Repair course will call you today.", "3 days ago", false),
    NotificationItem("5", "Reminder", "Android App Development batch registration closes in 5 days.", "1 week ago", false)
)

@Composable
fun NotificationsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd)))
                .statusBarsPadding().padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Notifications, null, tint = Color.White, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Notifications", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Stay updated on new batches", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(0.85f))
                }
            }
        }

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(sampleNotifications, key = { _, n -> n.id }) { _, notif ->
                NotificationCard(notif)
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun NotificationCard(notification: NotificationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(if (notification.isNew) 3.dp else 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isNew) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape)
                    .background(if (notification.isNew) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (notification.isNew) Icons.Filled.NotificationsActive else Icons.Filled.NotificationsNone,
                    null, tint = if (notification.isNew) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(notification.message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(6.dp))
                Text(notification.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
            if (notification.isNew) {
                Box(Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
            }
        }
    }
}
