package com.remziakgoz.cardlingua.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.presentation.ui.theme.righteousRegular
import androidx.hilt.navigation.compose.hiltViewModel
import com.remziakgoz.cardlingua.presentation.viewmodels.TranslationViewModel

@Composable
fun LevelCard(
    difficulty: Difficulty,
    isLocked: Boolean,
    progress: Float,
    onClick: () -> Unit,
    viewModel: TranslationViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(enabled = !isLocked, onClick = onClick)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) Color.Gray.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = when (difficulty) {
                        Difficulty.BEGINNER -> Brush.horizontalGradient(
                            listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                        )
                        Difficulty.INTERMEDIATE -> Brush.horizontalGradient(
                            listOf(Color(0xFF2196F3), Color(0xFF64B5F6))
                        )
                        Difficulty.ADVANCED -> Brush.horizontalGradient(
                            listOf(Color(0xFFF57C00), Color(0xFFFFB74D))
                        )
                        Difficulty.EXPERT -> Brush.horizontalGradient(
                            listOf(Color(0xFFE91E63), Color(0xFFF48FB1))
                        )
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = viewModel.getString(difficulty.name.lowercase()),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = righteousRegular,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = viewModel.getString("${difficulty.name.lowercase()}_desc"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                if (isLocked) {
                    Text(
                        text = viewModel.getString("locked"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

                Column {
                    Text(
                        text = "${(progress * 100).toInt()}% ${viewModel.getString("complete")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}