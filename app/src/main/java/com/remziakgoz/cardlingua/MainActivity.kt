package com.remziakgoz.cardlingua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.remziakgoz.cardlingua.presentation.components.FlipCard
import com.remziakgoz.cardlingua.presentation.ui.theme.CardLinguaTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.presentation.screens.GameOverScreen
import com.remziakgoz.cardlingua.presentation.screens.GameScreen
import com.remziakgoz.cardlingua.presentation.screens.LevelSelectionScreen
import kotlinx.coroutines.flow.collect
import androidx.activity.compose.BackHandler
import com.remziakgoz.cardlingua.presentation.screens.OnboardingScreen
import com.remziakgoz.cardlingua.data.preferences.PreferencesManager
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.rememberCoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardLinguaTheme {
                var showOnboarding by remember { mutableStateOf(true) }
                var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
                val scope = rememberCoroutineScope()

                // Check if user has seen onboarding
                LaunchedEffect(Unit) {
                    showOnboarding = !preferencesManager.hasSeenOnboarding()
                }

                if (showOnboarding) {
                    OnboardingScreen(
                        onComplete = {
                            showOnboarding = false
                            scope.launch {
                                preferencesManager.setHasSeenOnboarding(true)
                            }
                        }
                    )
                } else {
                    // Handle system back press
                    BackHandler(enabled = selectedDifficulty != null) {
                        selectedDifficulty = null
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when {
                            selectedDifficulty == null -> {
                                LevelSelectionScreen(
                                    onDifficultySelected = { difficulty ->
                                        selectedDifficulty = difficulty
                                    }
                                )
                            }
                            else -> {
                                GameScreen(
                                    difficulty = selectedDifficulty!!,
                                    onBackPressed = {
                                        selectedDifficulty = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}