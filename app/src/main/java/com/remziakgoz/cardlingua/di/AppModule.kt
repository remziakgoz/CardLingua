package com.remziakgoz.cardlingua.di

import com.remziakgoz.cardlingua.data.repository.TranslationRepository
import com.remziakgoz.cardlingua.data.preferences.PreferencesManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import com.remziakgoz.cardlingua.data.language.LanguageManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideLanguageManager(preferencesManager: PreferencesManager): LanguageManager {
        return LanguageManager(preferencesManager)
    }

    @Provides
    @Singleton
    fun provideTranslationRepository(
        preferencesManager: PreferencesManager,
        languageManager: LanguageManager
    ): TranslationRepository {
        return TranslationRepository(preferencesManager, languageManager)
    }
} 