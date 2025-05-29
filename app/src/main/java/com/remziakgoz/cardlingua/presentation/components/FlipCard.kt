package com.remziakgoz.cardlingua.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.blur
import kotlin.math.abs
import kotlin.math.PI
import kotlin.math.sin


@Composable
fun FlipCard(
    frontText: String,
    backText: String,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    emoji: String? = null
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isFlipped by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var showCheckmark by remember { mutableStateOf(false) }
    var showCross by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Add floating emoji above the card, only visible when card is flipped
        if (!emoji.isNullOrEmpty() && rotation.value > 90f) {
            Box(
                modifier = Modifier
                    .offset(y = (-200).dp)
                    .size(80.dp)
                    .graphicsLayer {
                        translationY = sin(rotation.value * (PI / 180)).toFloat() * 8
                        alpha = ((rotation.value - 90f) / 90f).coerceIn(0f, 1f)
                    },
                contentAlignment = Alignment.Center
            ) {
                // Glow effect
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .graphicsLayer {
                            alpha = 0.3f
                        }
                        .blur(radius = 20.dp)
                        .background(
                            color = Color(0xFF03DAC5),
                            shape = CircleShape
                        )
                )
                
                // Simple text display for all items
                Text(
                    text = emoji,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Checkmark overlay (green tick)
        if (showCheckmark) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Correct",
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF4CAF50)
            )
        }

        // Cross overlay (red X)
        if (showCross) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "Incorrect",
                modifier = Modifier.size(80.dp),
                tint = Color(0xFFE53935)
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta * 0.5f  // Reduce swipe speed by half
                    },
                    onDragStopped = {
                        coroutineScope.launch {
                            when {
                                offsetX < -100 -> {
                                    showCheckmark = true
                                    launch {
                                        animate(offsetX, -1000f) { value, _ ->
                                            offsetX = value
                                        }
                                    }.join()
                                    isFlipped = false
                                    rotation.snapTo(0f)
                                    offsetX = 0f
                                    showCheckmark = false
                                    onSwipeLeft()
                                }
                                offsetX > 100 -> {
                                    showCross = true
                                    launch {
                                        animate(offsetX, 1000f) { value, _ ->
                                            offsetX = value
                                        }
                                    }.join()
                                    isFlipped = false
                                    rotation.snapTo(0f)
                                    offsetX = 0f
                                    showCross = false
                                    onSwipeRight()
                                }
                                else -> {
                                    animate(offsetX, 0f) { value, _ ->
                                        offsetX = value
                                    }
                                }
                            }
                        }
                    }
                )
        ) {
            Card(
                modifier = Modifier
                    .size(320.dp, 220.dp)
                    .clickable {
                        coroutineScope.launch {
                            isFlipped = !isFlipped
                            rotation.animateTo(
                                targetValue = if (isFlipped) 180f else 0f,
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                    .graphicsLayer {
                        rotationY = rotation.value
                        cameraDistance = 12f * 16.dp.value
                        alpha = 1f - (abs(offsetX) / 1000f).coerceIn(0f, 0.5f)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (rotation.value <= 90f) 
                        Color(0xFF6200EE)
                    else 
                        Color(0xFF03DAC5)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (rotation.value <= 90f) {
                            // Front side (remove emoji from here)
                            Text(
                                text = frontText,
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = "Tap to see translation",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        } else {
                            // Back side
                            Text(
                                text = backText,
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.graphicsLayer {
                                    rotationY = 180f
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}