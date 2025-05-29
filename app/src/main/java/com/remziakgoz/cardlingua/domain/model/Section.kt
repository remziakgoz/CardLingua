package com.remziakgoz.cardlingua.domain.model

data class Section(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val iconName: String,
    val order: Int,
    val words: List<Word>,
    val isLocked: Boolean = true,
    val progress: Float = 0f
) 