package com.remziakgoz.cardlingua.data.language

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.remziakgoz.cardlingua.data.preferences.PreferencesManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

@Singleton
class LanguageManager @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _currentLanguage = MutableStateFlow("en")

    // Get the system's default locale
    private val systemLocale = Locale.getDefault().language
    
    // Initialize with system language if it's supported, otherwise default to English
    private val defaultLanguage = when (systemLocale) {
        "tr" -> "tr"
        else -> "en"
    }

    val currentLanguage: Flow<String> = preferencesManager.getLanguage().map { savedLanguage ->
        savedLanguage ?: defaultLanguage
    }

    val strings = mapOf(
        "app_name" to mapOf(
            "en" to "CardLingua",
            "tr" to "CardLingua"
        ),
        "app_subtitle" to mapOf(
            "en" to "Learn English with Flashcards",
            "tr" to "Kartlarla İngilizce Öğren"
        ),
        "beginner" to mapOf(
            "en" to "Beginner",
            "tr" to "Başlangıç"
        ),
        "beginner_desc" to mapOf(
            "en" to "Basic Words",
            "tr" to "Temel Kelimeler"
        ),
        "intermediate" to mapOf(
            "en" to "Intermediate",
            "tr" to "Orta Seviye"
        ),
        "intermediate_desc" to mapOf(
            "en" to "Common Phrases",
            "tr" to "Günlük İfadeler"
        ),
        "advanced" to mapOf(
            "en" to "Advanced",
            "tr" to "İleri Seviye"
        ),
        "advanced_desc" to mapOf(
            "en" to "Complex Words",
            "tr" to "Karmaşık Kelimeler"
        ),
        "expert" to mapOf(
            "en" to "Expert",
            "tr" to "Uzman"
        ),
        "expert_desc" to mapOf(
            "en" to "Advanced Terms",
            "tr" to "İleri Düzey Terimler"
        ),
        "loading" to mapOf(
            "en" to "Loading...",
            "tr" to "Yükleniyor..."
        ),
        "locked" to mapOf(
            "en" to "Locked",
            "tr" to "Kilitli"
        ),
        "complete" to mapOf(
            "en" to "Complete",
            "tr" to "Tamamlandı"
        ),
        "score" to mapOf(
            "en" to "Score",
            "tr" to "Puan"
        ),
        "accuracy" to mapOf(
            "en" to "Accuracy",
            "tr" to "Doğruluk"
        ),
        "correct_answers" to mapOf(
            "en" to "Correct Answers",
            "tr" to "Doğru Cevaplar"
        ),
        "incorrect_answers" to mapOf(
            "en" to "Incorrect Answers",
            "tr" to "Yanlış Cevaplar"
        ),
        "play_again" to mapOf(
            "en" to "Try Again",
            "tr" to "Tekrar Dene"
        ),
        "back_to_menu" to mapOf(
            "en" to "Back to Menu",
            "tr" to "Menüye Dön"
        ),
        "greetings" to mapOf(
            "en" to "Greetings",
            "tr" to "Selamlaşma"
        ),
        "greetings_desc" to mapOf(
            "en" to "Essential everyday greetings",
            "tr" to "Günlük temel selamlaşmalar"
        ),
        "basic_greetings" to mapOf(
            "en" to "Basic Greetings",
            "tr" to "Temel Selamlaşma"
        ),
        "basic_greetings_desc" to mapOf(
            "en" to "Learn essential greetings",
            "tr" to "Temel selamlaşmaları öğren"
        ),
        "numbers" to mapOf(
            "en" to "Numbers",
            "tr" to "Sayılar"
        ),
        "numbers_desc" to mapOf(
            "en" to "Basic numbers and counting",
            "tr" to "Temel sayılar ve sayma"
        ),
        "colors" to mapOf(
            "en" to "Colors",
            "tr" to "Renkler"
        ),
        "colors_desc" to mapOf(
            "en" to "Basic colors",
            "tr" to "Temel renkler"
        ),
        "animals" to mapOf(
            "en" to "Animals",
            "tr" to "Hayvanlar"
        ),
        "animals_desc" to mapOf(
            "en" to "Common animals",
            "tr" to "Yaygın hayvanlar"
        ),
        "food" to mapOf(
            "en" to "Food",
            "tr" to "Yiyecekler"
        ),
        "food_desc" to mapOf(
            "en" to "Common foods and drinks",
            "tr" to "Yaygın yiyecek ve içecekler"
        ),
        "food_drinks" to mapOf(
            "en" to "Food & Drinks",
            "tr" to "Yiyecek & İçecek"
        ),
        "food_drinks_desc" to mapOf(
            "en" to "Common foods and drinks",
            "tr" to "Temel yiyecek ve içecekler"
        ),
        "family" to mapOf(
            "en" to "Family",
            "tr" to "Aile"
        ),
        "family_desc" to mapOf(
            "en" to "Family members",
            "tr" to "Aile üyeleri"
        ),
        "time" to mapOf(
            "en" to "Time",
            "tr" to "Zaman"
        ),
        "time_desc" to mapOf(
            "en" to "Time expressions",
            "tr" to "Zaman ifadeleri"
        ),
        "weather" to mapOf(
            "en" to "Weather",
            "tr" to "Hava Durumu"
        ),
        "weather_desc" to mapOf(
            "en" to "Weather conditions",
            "tr" to "Hava durumu ifadeleri"
        ),
        "progress" to mapOf(
            "en" to "Progress",
            "tr" to "İlerleme"
        ),
        "percent_complete" to mapOf(
            "en" to "% Complete",
            "tr" to "% Tamamlandı"
        ),
        "completed" to mapOf(
            "en" to "Completed",
            "tr" to "Tamamlandı"
        ),
        "game_over" to mapOf(
            "en" to "Congratulations! You have completed all the cards!",
            "tr" to "Tebrikler! Tüm kartları tamamladınız!"
        ),
        "body_parts" to mapOf(
            "en" to "Body Parts",
            "tr" to "Vücut Bölümleri"
        ),
        "body_parts_desc" to mapOf(
            "en" to "Human body parts",
            "tr" to "İnsan vücudu bölümleri"
        ),
        "common_objects" to mapOf(
            "en" to "Common Objects",
            "tr" to "Günlük Eşyalar"
        ),
        "common_objects_desc" to mapOf(
            "en" to "Everyday items",
            "tr" to "Günlük kullanılan eşyalar"
        ),
        "daily_conversations" to mapOf(
            "en" to "Daily Conversations",
            "tr" to "Günlük Konuşmalar"
        ),
        "daily_conversations_desc" to mapOf(
            "en" to "Basic daily dialogues",
            "tr" to "Temel günlük diyaloglar"
        ),
        "shopping_phrases" to mapOf(
            "en" to "Shopping Phrases",
            "tr" to "Alışveriş İfadeleri"
        ),
        "shopping_phrases_desc" to mapOf(
            "en" to "Common shopping expressions",
            "tr" to "Temel alışveriş ifadeleri"
        ),
        "travel_phrases" to mapOf(
            "en" to "Travel Phrases",
            "tr" to "Seyahat İfadeleri"
        ),
        "travel_phrases_desc" to mapOf(
            "en" to "Essential travel expressions",
            "tr" to "Temel seyahat ifadeleri"
        ),
        "restaurant_phrases" to mapOf(
            "en" to "Restaurant Phrases",
            "tr" to "Restoran İfadeleri"
        ),
        "restaurant_phrases_desc" to mapOf(
            "en" to "Dining and ordering",
            "tr" to "Yemek siparişi ve lokanta"
        ),
        "hobbies_interests" to mapOf(
            "en" to "Hobbies & Interests",
            "tr" to "Hobiler ve İlgi Alanları"
        ),
        "hobbies_interests_desc" to mapOf(
            "en" to "Leisure activities",
            "tr" to "Boş zaman aktiviteleri"
        ),
        "emotions_feelings" to mapOf(
            "en" to "Emotions & Feelings",
            "tr" to "Duygular ve Hisler"
        ),
        "emotions_feelings_desc" to mapOf(
            "en" to "Expressing emotions",
            "tr" to "Duyguları ifade etme"
        ),
        "weather_climate" to mapOf(
            "en" to "Weather & Climate",
            "tr" to "Hava Durumu"
        ),
        "weather_climate_desc" to mapOf(
            "en" to "Weather expressions",
            "tr" to "Hava durumu ifadeleri"
        ),
        "health_fitness" to mapOf(
            "en" to "Health & Fitness",
            "tr" to "Sağlık ve Spor"
        ),
        "health_fitness_desc" to mapOf(
            "en" to "Health and wellness terms",
            "tr" to "Sağlık ve spor terimleri"
        ),
        "technology_daily" to mapOf(
            "en" to "Daily Technology",
            "tr" to "Günlük Teknoloji"
        ),
        "technology_daily_desc" to mapOf(
            "en" to "Common tech terms",
            "tr" to "Günlük teknoloji terimleri"
        ),
        "sports_fitness" to mapOf(
            "en" to "Sports & Games",
            "tr" to "Spor ve Oyunlar"
        ),
        "sports_fitness_desc" to mapOf(
            "en" to "Sports vocabulary",
            "tr" to "Spor terimleri"
        ),
        "transportation" to mapOf(
            "en" to "Transportation",
            "tr" to "Ulaşım"
        ),
        "transportation_desc" to mapOf(
            "en" to "Vehicles and transport modes",
            "tr" to "Taşıtlar ve ulaşım araçları"
        ),
        "entertainment" to mapOf(
            "en" to "Entertainment",
            "tr" to "Eğlence"
        ),
        "entertainment_desc" to mapOf(
            "en" to "Leisure and entertainment",
            "tr" to "Eğlence ve aktiviteler"
        ),
        "business_turkish" to mapOf(
            "en" to "Business English",
            "tr" to "İş İngilizcesi"
        ),
        "business_turkish_desc" to mapOf(
            "en" to "Business terminology",
            "tr" to "İş dünyası terimleri"
        ),
        "medical_terms" to mapOf(
            "en" to "Medical Terms",
            "tr" to "Tıbbi Terimler"
        ),
        "medical_terms_desc" to mapOf(
            "en" to "Healthcare vocabulary",
            "tr" to "Sağlık terimleri"
        ),
        "technology" to mapOf(
            "en" to "Technology",
            "tr" to "Teknoloji"
        ),
        "technology_desc" to mapOf(
            "en" to "Tech terminology",
            "tr" to "Teknoloji terimleri"
        ),
        "legal_terms" to mapOf(
            "en" to "Legal Terms",
            "tr" to "Hukuki Terimler"
        ),
        "legal_terms_desc" to mapOf(
            "en" to "Legal terminology",
            "tr" to "Hukuk terimleri"
        ),
        "environment" to mapOf(
            "en" to "Environment",
            "tr" to "Çevre"
        ),
        "environment_desc" to mapOf(
            "en" to "Environmental terms",
            "tr" to "Çevre terimleri"
        ),
        "psychology" to mapOf(
            "en" to "Psychology",
            "tr" to "Psikoloji"
        ),
        "psychology_desc" to mapOf(
            "en" to "Psychological terms",
            "tr" to "Psikoloji terimleri"
        ),
        "arts_culture" to mapOf(
            "en" to "Arts & Culture",
            "tr" to "Sanat ve Kültür"
        ),
        "arts_culture_desc" to mapOf(
            "en" to "Cultural terminology",
            "tr" to "Kültür ve sanat terimleri"
        ),
        "social_media" to mapOf(
            "en" to "Social Media",
            "tr" to "Sosyal Medya"
        ),
        "social_media_desc" to mapOf(
            "en" to "Social media terms",
            "tr" to "Sosyal medya terimleri"
        ),
        "science_nature" to mapOf(
            "en" to "Science & Nature",
            "tr" to "Bilim ve Doğa"
        ),
        "science_nature_desc" to mapOf(
            "en" to "Scientific concepts",
            "tr" to "Bilimsel kavramlar"
        ),
        "politics" to mapOf(
            "en" to "Politics",
            "tr" to "Siyaset"
        ),
        "politics_desc" to mapOf(
            "en" to "Political terminology",
            "tr" to "Siyasi terimler"
        ),
        "media_journalism" to mapOf(
            "en" to "Media & Journalism",
            "tr" to "Medya ve Gazetecilik"
        ),
        "media_journalism_desc" to mapOf(
            "en" to "Media terminology",
            "tr" to "Medya terimleri"
        ),
        "architecture_design" to mapOf(
            "en" to "Architecture & Design",
            "tr" to "Mimarlık ve Tasarım"
        ),
        "architecture_design_desc" to mapOf(
            "en" to "Design concepts",
            "tr" to "Tasarım kavramları"
        ),
        "literature" to mapOf(
            "en" to "Literature",
            "tr" to "Edebiyat"
        ),
        "literature_desc" to mapOf(
            "en" to "Literary terms",
            "tr" to "Edebi terimler"
        ),
        "academic_turkish" to mapOf(
            "en" to "Academic English",
            "tr" to "Akademik İngilizce"
        ),
        "academic_turkish_desc" to mapOf(
            "en" to "Academic terminology",
            "tr" to "Akademik terimler"
        ),
        "philosophy" to mapOf(
            "en" to "Philosophy",
            "tr" to "Felsefe"
        ),
        "philosophy_desc" to mapOf(
            "en" to "Philosophical concepts",
            "tr" to "Felsefi kavramlar"
        ),
        "scientific_research" to mapOf(
            "en" to "Scientific Research",
            "tr" to "Bilimsel Araştırma"
        ),
        "scientific_research_desc" to mapOf(
            "en" to "Research methodology",
            "tr" to "Araştırma metodolojisi"
        ),
        "economics" to mapOf(
            "en" to "Economics",
            "tr" to "Ekonomi"
        ),
        "economics_desc" to mapOf(
            "en" to "Economic terminology",
            "tr" to "Ekonomi terimleri"
        ),
        "international_relations" to mapOf(
            "en" to "International Relations",
            "tr" to "Uluslararası İlişkiler"
        ),
        "international_relations_desc" to mapOf(
            "en" to "Diplomatic terms",
            "tr" to "Diplomasi terimleri"
        ),
        "law_justice" to mapOf(
            "en" to "Law & Justice",
            "tr" to "Hukuk ve Adalet"
        ),
        "law_justice_desc" to mapOf(
            "en" to "Legal system terms",
            "tr" to "Hukuk sistemi terimleri"
        ),
        "engineering" to mapOf(
            "en" to "Engineering",
            "tr" to "Mühendislik"
        ),
        "engineering_desc" to mapOf(
            "en" to "Technical terminology",
            "tr" to "Teknik terimler"
        ),
        "finance_banking" to mapOf(
            "en" to "Finance & Banking",
            "tr" to "Finans ve Bankacılık"
        ),
        "finance_banking_desc" to mapOf(
            "en" to "Financial terminology",
            "tr" to "Finans terimleri"
        ),
        "philosophy_ethics" to mapOf(
            "en" to "Philosophy & Ethics",
            "tr" to "Felsefe ve Etik"
        ),
        "philosophy_ethics_desc" to mapOf(
            "en" to "Philosophical terms",
            "tr" to "Felsefi terimler"
        ),
        "quantum_physics" to mapOf(
            "en" to "Quantum Physics",
            "tr" to "Kuantum Fiziği"
        ),
        "quantum_physics_desc" to mapOf(
            "en" to "Physics terminology",
            "tr" to "Fizik terimleri"
        ),
        "neuroscience" to mapOf(
            "en" to "Neuroscience",
            "tr" to "Nörobilim"
        ),
        "neuroscience_desc" to mapOf(
            "en" to "Brain science terms",
            "tr" to "Beyin bilimi terimleri"
        ),
        "remaining_cards" to mapOf(
            "en" to "Cards",
            "tr" to "Kart"
        ),
        "onboarding_swipe_title" to mapOf(
            "en" to "Swipe to Learn",
            "tr" to "Öğrenmek için Kaydır"
        ),
        "onboarding_swipe_desc" to mapOf(
            "en" to "Swipe right if you don't know the word, left if you do. Tap to see translation.",
            "tr" to "Kelimeyi bilmiyorsanız sağa, biliyorsanız sola kaydırın. Çeviri için dokunun."
        ),
        "onboarding_progress_title" to mapOf(
            "en" to "Track Progress",
            "tr" to "İlerlemenizi Takip Edin"
        ),
        "onboarding_progress_desc" to mapOf(
            "en" to "See your progress in each category and unlock new levels",
            "tr" to "Her kategorideki ilerlemenizi görün ve yeni seviyelerin kilidini açın"
        ),
        "onboarding_language_title" to mapOf(
            "en" to "Switch Languages",
            "tr" to "Dil Değiştirin"
        ),
        "onboarding_language_desc" to mapOf(
            "en" to "Switch between English and Turkish anytime",
            "tr" to "İstediğiniz zaman İngilizce ve Türkçe arasında geçiş yapın"
        ),
        "get_started" to mapOf(
            "en" to "Get Started",
            "tr" to "Başla"
        ),
        "skip" to mapOf(
            "en" to "Skip",
            "tr" to "Atla"
        ),
        "next" to mapOf(
            "en" to "Next",
            "tr" to "İleri"
        ),
        "swipe_left_correct" to mapOf(
            "en" to "Swipe left if you know",
            "tr" to "Biliyorsanız sola kaydırın"
        ),
        "swipe_right_incorrect" to mapOf(
            "en" to "Swipe right if you don't",
            "tr" to "Bilmiyorsanız sağa kaydırın"
        ),
        "unlock_requirement_desc" to mapOf(
            "en" to "Complete 70% of the previous category to unlock the next one",
            "tr" to "Bir sonraki kategoriyi açmak için önceki kategorinin %70'ini tamamlayın"
        ),
        "clothing" to mapOf(
            "en" to "Clothing",
            "tr" to "Kıyafetler"
        ),
        "clothing_desc" to mapOf(
            "en" to "Basic clothing items",
            "tr" to "Temel kıyafet eşyaları"
        ),
        "professions" to mapOf(
            "en" to "Professions",
            "tr" to "Meslekler"
        ),
        "professions_desc" to mapOf(
            "en" to "Common jobs and occupations",
            "tr" to "Yaygın meslekler"
        ),
        "school_items" to mapOf(
            "en" to "School Items",
            "tr" to "Okul Eşyaları"
        ),
        "school_items_desc" to mapOf(
            "en" to "Common school supplies",
            "tr" to "Temel okul malzemeleri"
        ),
        "sports_equipment" to mapOf(
            "en" to "Sports Equipment",
            "tr" to "Spor Ekipmanları"
        ),
        "sports_equipment_desc" to mapOf(
            "en" to "Common sports gear",
            "tr" to "Yaygın spor malzemeleri"
        ),
        "office_workplace" to mapOf(
            "en" to "Office & Workplace",
            "tr" to "Ofis ve İş Yeri"
        ),
        "office_workplace_desc" to mapOf(
            "en" to "Office terminology and workplace vocabulary",
            "tr" to "Ofis terimleri ve iş yeri kelimeleri"
        ),
        "home_furniture" to mapOf(
            "en" to "Home & Furniture",
            "tr" to "Ev ve Mobilya"
        ),
        "home_furniture_desc" to mapOf(
            "en" to "Home items and furniture vocabulary",
            "tr" to "Ev eşyaları ve mobilya kelimeleri"
        ),
        "personal_care" to mapOf(
            "en" to "Personal Care",
            "tr" to "Kişisel Bakım"
        ),
        "personal_care_desc" to mapOf(
            "en" to "Personal hygiene and care items",
            "tr" to "Kişisel hijyen ve bakım ürünleri"
        ),
        "social_situations" to mapOf(
            "en" to "Social Situations",
            "tr" to "Sosyal Durumlar"
        ),
        "social_situations_desc" to mapOf(
            "en" to "Common social events and gatherings",
            "tr" to "Yaygın sosyal etkinlikler ve toplantılar"
        ),
        "electronics_gadgets" to mapOf(
            "en" to "Electronics & Gadgets",
            "tr" to "Elektronik Cihazlar"
        ),
        "electronics_gadgets_desc" to mapOf(
            "en" to "Modern electronic devices",
            "tr" to "Modern elektronik cihazlar"
        ),
        "astronomy_space" to mapOf(
            "en" to "Astronomy & Space",
            "tr" to "Astronomi ve Uzay"
        ),
        "astronomy_space_desc" to mapOf(
            "en" to "Space and celestial objects",
            "tr" to "Uzay ve gök cisimleri"
        ),
        "mythology_folklore" to mapOf(
            "en" to "Mythology & Folklore",
            "tr" to "Mitoloji ve Halk Bilimi"
        ),
        "mythology_folklore_desc" to mapOf(
            "en" to "Ancient myths and cultural stories",
            "tr" to "Antik mitler ve kültürel hikayeler"
        ),
        "cryptography_security" to mapOf(
            "en" to "Cryptography & Security",
            "tr" to "Kriptografi ve Güvenlik"
        ),
        "cryptography_security_desc" to mapOf(
            "en" to "Digital security and encryption",
            "tr" to "Dijital güvenlik ve şifreleme"
        ),
        "biochemistry" to mapOf(
            "en" to "Biochemistry",
            "tr" to "Biyokimya"
        ),
        "biochemistry_desc" to mapOf(
            "en" to "Molecular biology and biochemical processes",
            "tr" to "Moleküler biyoloji ve biyokimyasal süreçler"
        )
    )

    init {
        // Load saved language preference
        scope.launch {
            preferencesManager.getLanguage().collect { savedLanguage ->
                _currentLanguage.value = savedLanguage ?: defaultLanguage
            }
        }
    }

    suspend fun setLanguage(languageCode: String) {
        preferencesManager.saveLanguage(languageCode)
        _currentLanguage.value = languageCode
    }

    fun getString(key: String): String {
        return strings[key]?.get(_currentLanguage.value) ?: key
    }
} 