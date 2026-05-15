package com.nammaskill.app.ui.stories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nammaskill.app.data.model.SuccessStory
import com.nammaskill.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SuccessStoriesScreen(viewModel: SuccessStoriesViewModel = hiltViewModel()) {
    val stories by viewModel.stories.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(GradientAmberStart, GradientAmberEnd)))
                .statusBarsPadding().padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.EmojiEvents, null, tint = Color.White, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Success Stories", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Real people, real transformations", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
                }
            }
        }
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            itemsIndexed(stories, key = { _, s -> s.id }) { index, story ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(story.id) { delay(index * 80L); visible = true }
                AnimatedVisibility(visible, enter = fadeIn() + slideInVertically { it / 3 }) {
                    StoryCard(story)
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun StoryCard(story: SuccessStory) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(52.dp).clip(CircleShape).background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary))), contentAlignment = Alignment.Center) {
                    Text(story.name.split(" ").take(2).map { it.first().uppercase() }.joinToString(""), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(story.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("${story.location} • Age ${story.age}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(Modifier.clip(RoundedCornerShape(8.dp)).background(JobGuaranteeGreen.copy(alpha = 0.1f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text("${story.completionYear}", style = MaterialTheme.typography.labelSmall, color = JobGuaranteeGreen, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(Modifier.clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.primaryContainer).padding(horizontal = 8.dp, vertical = 3.dp)) {
                Text("📚 ${story.courseCompleted}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.WorkOutline, null, tint = JobGuaranteeGreen, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(story.jobAchieved, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = JobGuaranteeGreen)
            }
            Spacer(Modifier.height(12.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh), shape = RoundedCornerShape(12.dp)) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Filled.FormatQuote, null, tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(story.testimonial, style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
