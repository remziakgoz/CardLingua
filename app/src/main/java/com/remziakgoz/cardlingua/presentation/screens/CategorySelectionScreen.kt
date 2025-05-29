package com.remziakgoz.cardlingua.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.domain.model.Section
import com.remziakgoz.cardlingua.presentation.components.CategoryCard
import com.remziakgoz.cardlingua.presentation.components.CenteredTopBar
import com.remziakgoz.cardlingua.presentation.viewmodels.TranslationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun CategorySelectionScreen(
    difficulty: Difficulty,
    sections: List<Section>,
    onCategorySelected: (Section) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: TranslationViewModel = hiltViewModel()
) {
    val currentSections by viewModel.sectionsState.collectAsState()
    val listState = rememberLazyListState()
    
    // Animation states
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }
    val slideOffset = remember { Animatable(0f) }
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    
    // Initial entry animation
    LaunchedEffect(Unit) {
        // Start from hidden state
        alpha.snapTo(0f)
        scale.snapTo(0.9f)
        slideOffset.snapTo(100f)
        
        // Animate entry
        coroutineScope {
            launch {
                slideOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(400)
                )
            }
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }
    }

    // Different animations for each difficulty
    suspend fun animateBasedOnDifficulty(scope: CoroutineScope) {
        when (difficulty) {
            Difficulty.BEGINNER -> {
                // Playful card reveal animation for beginners
                coroutineScope {
                    alpha.snapTo(1f) // Keep content visible
                    scale.snapTo(0.3f)
                    scale.animateTo(
                        1f,
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
            Difficulty.INTERMEDIATE -> {
                // Slide and rotate animation with a more dramatic effect
                coroutineScope {
                    alpha.snapTo(1f) // Keep content visible
                    scale.snapTo(0.3f)
                    scale.animateTo(
                        1f,
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
            Difficulty.ADVANCED -> {
                // Bounce animation
                coroutineScope {
                    alpha.snapTo(1f) // Keep content visible
                    scale.snapTo(0.3f)
                    scale.animateTo(
                        1f,
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
            Difficulty.EXPERT -> {
                // Complex 3D animation for expert level
                coroutineScope {
                    alpha.snapTo(1f) // Keep content visible
                    scale.snapTo(0.3f)
                    scale.animateTo(
                        1f,
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
    }

    // Only animate when returning from game
    LaunchedEffect(viewModel.isReturningFromGame) {
        if (viewModel.isReturningFromGame) {
            animateBasedOnDifficulty(this)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value,
                alpha = alpha.value,
                rotationZ = rotation.value,
                rotationX = rotationX.value,
                rotationY = rotationY.value,
                translationX = slideOffset.value,
                cameraDistance = 12f * LocalDensity.current.density
            )
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = currentSections.filter { it.difficulty == difficulty },
                key = { it.id }
            ) { section ->
                CategoryCard(
                    section = section,
                    progress = viewModel.getSectionProgress(section.id),
                    onClick = { onCategorySelected(section) }
                )
            }
        }
    }
} 