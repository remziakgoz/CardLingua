package com.remziakgoz.cardlingua.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.presentation.components.LevelCard
import com.remziakgoz.cardlingua.presentation.components.LanguageSelector
import com.remziakgoz.cardlingua.presentation.ui.theme.righteousRegular
import com.remziakgoz.cardlingua.presentation.viewmodels.TranslationViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun LevelSelectionScreen(
    onDifficultySelected: (Difficulty) -> Unit,
    viewModel: TranslationViewModel = hiltViewModel()
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    val cardLinguaPurple = Color(0xFF6200EE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {
        // Language selector with adjusted padding
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp), // Reduced top padding
            contentAlignment = Alignment.TopEnd
        ) {
            LanguageSelector(
                currentLanguage = currentLanguage,
                onLanguageSelected = { language ->
                    viewModel.setLanguage(language)
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = viewModel.getString("app_name"),
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = righteousRegular,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    shadow = Shadow(
                        color = Color(0xFF6200EE),
                        offset = Offset(0f, 4f),
                        blurRadius = 8f
                    )
                ),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = viewModel.getString("app_subtitle"),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                ),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                cardLinguaPurple.copy(alpha = 0.2f),
                                cardLinguaPurple,
                                cardLinguaPurple.copy(alpha = 0.2f),
                            )
                        )
                    )
            )
        }

        // Difficulty Level Cards
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            items(Difficulty.values()) { difficulty ->
                // All levels are now unlocked
                val isLocked = false  // Changed from the previous locking logic

                LevelCard(
                    difficulty = difficulty,
                    isLocked = isLocked,
                    progress = viewModel.getDifficultyProgress(difficulty),
                    onClick = {
                        // Removed the isLocked check
                        onDifficultySelected(difficulty)
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

