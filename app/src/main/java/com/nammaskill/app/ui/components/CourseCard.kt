package com.nammaskill.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nammaskill.app.data.model.Course
import com.nammaskill.app.ui.theme.ChipCoding
import com.nammaskill.app.ui.theme.ChipElectrician
import com.nammaskill.app.ui.theme.ChipMobileRepair
import com.nammaskill.app.ui.theme.ChipSewing
import com.nammaskill.app.ui.theme.JobGuaranteeGreen
import com.nammaskill.app.ui.theme.JobGuaranteeRed

@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = when (course.category) {
        "Electrician" -> ChipElectrician
        "Sewing" -> ChipSewing
        "Coding" -> ChipCoding
        "Mobile Repair" -> ChipMobileRepair
        else -> MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Top gradient header with category
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                categoryColor,
                                categoryColor.copy(alpha = 0.6f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Title row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Job Guarantee Badge
                    JobGuaranteeBadge(hasGuarantee = course.jobGuarantee)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Category chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(categoryColor.copy(alpha = 0.12f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = course.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = categoryColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Info row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Duration
                    InfoChip(
                        icon = Icons.Filled.Schedule,
                        text = course.duration,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Start Date
                    InfoChip(
                        icon = Icons.Filled.CalendarMonth,
                        text = course.startDate,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Location
                InfoChip(
                    icon = Icons.Filled.LocationOn,
                    text = course.location,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun JobGuaranteeBadge(
    hasGuarantee: Boolean,
    modifier: Modifier = Modifier
) {
    val bgColor = if (hasGuarantee) JobGuaranteeGreen else JobGuaranteeRed
    val icon = if (hasGuarantee) Icons.Filled.CheckCircle else Icons.Filled.RemoveCircle
    val text = if (hasGuarantee) "Job ✓" else "No Job"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = if (hasGuarantee) "Job Guaranteed" else "No Job Guarantee",
                tint = bgColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = bgColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
