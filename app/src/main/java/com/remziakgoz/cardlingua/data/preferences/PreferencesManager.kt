package com.remziakgoz.cardlingua.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.booleanPreferencesKey

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cardlingua_preferences")

@Singleton
class PreferencesManager @Inject constructor(
    private val context: Context
) {
    private val sectionProgressPrefix = "section_progress_"
    private val sectionUnlockedPrefix = "section_unlocked_"
    private val difficultyProgressPrefix = "difficulty_progress_"
    private val languageKey = stringPreferencesKey("app_language")
    private val hasSeenOnboardingKey = booleanPreferencesKey("has_seen_onboarding")

    suspend fun saveSectionProgress(sectionId: String, progress: Float) {
        val key = floatPreferencesKey("$sectionProgressPrefix$sectionId")
        context.dataStore.edit { preferences ->
            preferences[key] = progress
        }
    }

    suspend fun getSectionProgress(sectionId: String): Float {
        val key = floatPreferencesKey("$sectionProgressPrefix$sectionId")
        return context.dataStore.data.first()[key] ?: 0f
    }

    suspend fun saveDifficultyProgress(difficulty: String, progress: Float) {
        val key = floatPreferencesKey("$difficultyProgressPrefix$difficulty")
        context.dataStore.edit { preferences ->
            preferences[key] = progress
        }
    }

    suspend fun getDifficultyProgress(difficulty: String): Float {
        val key = floatPreferencesKey("$difficultyProgressPrefix$difficulty")
        return context.dataStore.data.first()[key] ?: 0f
    }

    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[languageKey] = languageCode
        }
    }

    fun getLanguage(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[languageKey]
        }
    }

    suspend fun saveSectionUnlocked(sectionId: String, isUnlocked: Boolean) {
        val key = booleanPreferencesKey("$sectionUnlockedPrefix$sectionId")
        context.dataStore.edit { preferences ->
            preferences[key] = isUnlocked
        }
    }

    suspend fun getSectionUnlocked(sectionId: String): Boolean {
        val key = booleanPreferencesKey("$sectionUnlockedPrefix$sectionId")
        return context.dataStore.data.first()[key] ?: false
    }

    suspend fun setHasSeenOnboarding(seen: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[hasSeenOnboardingKey] = seen
        }
    }

    suspend fun hasSeenOnboarding(): Boolean {
        return context.dataStore.data.first()[hasSeenOnboardingKey] ?: false
    }

    suspend fun saveLevelProgress(levelId: String, progress: Float) {
        val key = floatPreferencesKey("level_progress_$levelId")
        context.dataStore.edit { preferences ->
            preferences[key] = progress
        }
    }

    suspend fun getLevelProgress(levelId: String): Float {
        val key = floatPreferencesKey("level_progress_$levelId")
        return context.dataStore.data.first()[key] ?: 0f
    }
} 