package com.remziakgoz.cardlingua.domain.model

data class Word(
    val english: String,
    val turkish: String,
    val difficulty: Difficulty
)

enum class Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}

enum class BeginnerCategories {
    BASIC_WORDS,
    FAMILY_FRIENDS,
    DAILY_LIFE,
    COLORS_NUMBERS,
    ANIMALS,
    FOOD_DRINKS,
    CLOTHES,
    WEATHER,
    BODY_PARTS,
    TIME_CALENDAR
}