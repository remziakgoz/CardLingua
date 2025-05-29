package com.remziakgoz.cardlingua.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.domain.model.Section
import com.remziakgoz.cardlingua.presentation.components.FlipCard
import com.remziakgoz.cardlingua.presentation.viewmodels.TranslationViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import com.remziakgoz.cardlingua.presentation.ui.theme.righteousRegular
import com.remziakgoz.cardlingua.presentation.components.CenteredTopBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.border
import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.animateDpAsState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    difficulty: Difficulty,
    onBackPressed: () -> Unit,
    viewModel: TranslationViewModel = hiltViewModel()
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    
    LaunchedEffect(difficulty) {
        viewModel.loadSections(difficulty)
    }

    val currentSections by viewModel.sectionsState.collectAsState()

    if (currentSections.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = viewModel.getString("loading"))
        }
        return
    }

    BackHandler(enabled = viewModel.currentSection != null) {
        viewModel.onExitGame()
        viewModel.currentSection = null
    }

    Scaffold(
        topBar = {
            CenteredTopBar(
                title = viewModel.getString(difficulty.name.lowercase()),
                onBackPressed = {
                    if (viewModel.currentSection != null) {
                        viewModel.onExitGame()
                        viewModel.currentSection = null
                    } else {
                        onBackPressed()
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (viewModel.currentSection == null) {
                CategorySelectionScreen(
                    difficulty = difficulty,
                    sections = currentSections,
                    onCategorySelected = { section ->
                        viewModel.startGame(section)
                    },
                    onBackPressed = onBackPressed
                )
            } else {
                GameContent(
                    viewModel = viewModel,
                    onBackToCategories = {
                        viewModel.onExitGame()
                        viewModel.currentSection = null
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun GameContent(
    viewModel: TranslationViewModel,
    onBackToCategories: () -> Unit
) {
    if (viewModel.isGameOver) {
        GameOverScreen(
            score = viewModel.score,
            accuracy = viewModel.accuracy,
            correctWords = viewModel.getCorrectWords(),
            incorrectWords = viewModel.getIncorrectWords(),
            onPlayAgain = {
                viewModel.currentSection?.let { section ->
                    viewModel.startGame(section)
                }
            },
            onBackToMenu = onBackToCategories
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Progress section
            Column {
                // Modern circular progress with counter
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    // Outer circle with gradient
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    // Circular progress indicator
                    CircularProgressIndicator(
                        progress = viewModel.currentWordIndex.toFloat() / viewModel.words.size,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 5.dp
                    )

                    // Animated counter
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimatedContent(
                                targetState = viewModel.currentWordIndex + 1,
                                transitionSpec = {
                                    slideInVertically { height -> height } + fadeIn() with
                                    slideOutVertically { height -> -height } + fadeOut()
                                },
                                label = "card counter"
                            ) { count ->
                                Text(
                                    text = count.toString(),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = righteousRegular,
                                        fontSize = 28.sp,
                                        shadow = Shadow(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                            offset = Offset(0f, 2f),
                                            blurRadius = 3f
                                        )
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "/ ${viewModel.words.size}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = righteousRegular,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                ),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                // Linear progress indicator with rounded corners and gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(viewModel.currentWordIndex.toFloat() / viewModel.words.size)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                    )
                                )
                            )
                    )
                }
            }

            // Card section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val offsetX by animateDpAsState(
                        targetValue = when (viewModel.slideDirection) {
                            -1 -> (-1000).dp // Slide left
                            1 -> 1000.dp // Slide right
                            else -> 0.dp // Center
                        },
                        animationSpec = tween(1000),
                        label = "slide animation"
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = offsetX)
                    ) {
                        FlipCard(
                            frontText = viewModel.currentWord ?: viewModel.getString("loading"),
                            backText = viewModel.translatedWord ?: viewModel.getString("loading"),
                            onSwipeLeft = { 
                                if (!viewModel.isAnimatingBack) viewModel.onSwipeLeft() 
                            },
                            onSwipeRight = { 
                                if (!viewModel.isAnimatingBack) viewModel.onSwipeRight() 
                            },
                            emoji = viewModel.currentEmoji
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedRewindButton(
                        onClick = { viewModel.onRewind() }
                    )
                }
            }
        }
    }
}

// Add a pulsing animation for the counter
@Composable
private fun PulsingNumber(
    number: Int,
    modifier: Modifier = Modifier
) {
    var shouldAnimate by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (shouldAnimate) 1.2f else 1f,
        animationSpec = tween(200),
        label = "scale animation"
    )

    LaunchedEffect(number) {
        shouldAnimate = true
        delay(100)
        shouldAnimate = false
    }

    Text(
        text = number.toString(),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.scale(scale)
    )
}

@Composable
private fun AnimatedRewindButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) -45f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "rotation"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val gradientRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradient rotation"
    )

    Surface(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .size(56.dp),
        shape = CircleShape,
        color = Color.Transparent,
        shadowElevation = if (isPressed) 4.dp else 8.dp,
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFF9C27B0).copy(alpha = 0.8f),
                            Color(0xFF9C27B0),
                            Color(0xFF6200EE),
                            Color(0xFF9C27B0)
                        ),
                        center = Offset(0.5f, 0.5f)
                    ),
                    shape = CircleShape
                )
                .graphicsLayer {
                    rotationZ = gradientRotation
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationZ = -gradientRotation
                    },
                contentAlignment = Alignment.Center
            ) {
                // Enhanced glow effect
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xFF6200EE).copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .blur(radius = 8.dp)
                )
                
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous card",
                    modifier = Modifier
                        .size(28.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                            scaleX = 1.2f
                            scaleY = 1.2f
                        },
                    tint = Color.White
                )
            }
        }
    }
}