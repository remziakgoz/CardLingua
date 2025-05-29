package com.remziakgoz.cardlingua.data.repository

import com.remziakgoz.cardlingua.domain.model.Word
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.domain.model.Section
import com.remziakgoz.cardlingua.data.preferences.PreferencesManager
import com.remziakgoz.cardlingua.data.language.LanguageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val languageManager: LanguageManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private val _sectionsFlow = MutableStateFlow<List<Section>>(emptyList())
    val sectionsFlow: StateFlow<List<Section>> = _sectionsFlow

    private val sections = listOf(
        // BEGINNER SECTIONS (15 sections)
        Section(
            id = "basic_greetings",
            name = "Basic Greetings",
            description = "Learn essential greetings",
            difficulty = Difficulty.BEGINNER,
            iconName = "greetings",
            order = 1,
            isLocked = false, // First section is unlocked by default
            words = listOf(
                Word("Hello", "Merhaba", Difficulty.BEGINNER),
                Word("Good morning", "Günaydın", Difficulty.BEGINNER),
                Word("Good afternoon", "İyi günler", Difficulty.BEGINNER),
                Word("Good evening", "İyi akşamlar", Difficulty.BEGINNER),
                Word("Good night", "İyi geceler", Difficulty.BEGINNER),
                Word("Goodbye", "Hoşça kal", Difficulty.BEGINNER),
                Word("See you later", "Görüşürüz", Difficulty.BEGINNER),
                Word("How are you?", "Nasılsın?", Difficulty.BEGINNER),
                Word("I'm fine", "İyiyim", Difficulty.BEGINNER),
                Word("Thank you", "Teşekkür ederim", Difficulty.BEGINNER),
                Word("You're welcome", "Rica ederim", Difficulty.BEGINNER),
                Word("Please", "Lütfen", Difficulty.BEGINNER),
                Word("Nice to meet you", "Tanıştığıma memnun oldum", Difficulty.BEGINNER),
                Word("What's your name?", "Adın ne?", Difficulty.BEGINNER),
                Word("My name is...", "Benim adım...", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "numbers",
            name = "Numbers",
            description = "Basic numbers and counting",
            difficulty = Difficulty.BEGINNER,
            iconName = "numbers",
            order = 2,
            words = listOf(
                Word("Zero", "Sıfır", Difficulty.BEGINNER),
                Word("One", "Bir", Difficulty.BEGINNER),
                Word("Two", "İki", Difficulty.BEGINNER),
                Word("Three", "Üç", Difficulty.BEGINNER),
                Word("Four", "Dört", Difficulty.BEGINNER),
                Word("Five", "Beş", Difficulty.BEGINNER),
                Word("Six", "Altı", Difficulty.BEGINNER),
                Word("Seven", "Yedi", Difficulty.BEGINNER),
                Word("Eight", "Sekiz", Difficulty.BEGINNER),
                Word("Nine", "Dokuz", Difficulty.BEGINNER),
                Word("Ten", "On", Difficulty.BEGINNER),
                Word("Twenty", "Yirmi", Difficulty.BEGINNER),
                Word("Thirty", "Otuz", Difficulty.BEGINNER),
                Word("Forty", "Kırk", Difficulty.BEGINNER),
                Word("Fifty", "Elli", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "colors",
            name = "Colors",
            description = "Basic colors",
            difficulty = Difficulty.BEGINNER,
            iconName = "palette",
            order = 3,
            words = listOf(
                Word("Red", "Kırmızı", Difficulty.BEGINNER),
                Word("Blue", "Mavi", Difficulty.BEGINNER),
                Word("Green", "Yeşil", Difficulty.BEGINNER),
                Word("Yellow", "Sarı", Difficulty.BEGINNER),
                Word("Black", "Siyah", Difficulty.BEGINNER),
                Word("White", "Beyaz", Difficulty.BEGINNER),
                Word("Orange", "Turuncu", Difficulty.BEGINNER),
                Word("Purple", "Mor", Difficulty.BEGINNER),
                Word("Pink", "Pembe", Difficulty.BEGINNER),
                Word("Brown", "Kahverengi", Difficulty.BEGINNER),
                Word("Gray", "Gri", Difficulty.BEGINNER),
                Word("Gold", "Altın", Difficulty.BEGINNER),
                Word("Silver", "Gümüş", Difficulty.BEGINNER),
                Word("Light", "Açık", Difficulty.BEGINNER),
                Word("Dark", "Koyu", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "family",
            name = "Family Members",
            description = "Family relationships",
            difficulty = Difficulty.BEGINNER,
            iconName = "family",
            order = 4,
            words = listOf(
                Word("Mother", "Anne", Difficulty.BEGINNER),
                Word("Father", "Baba", Difficulty.BEGINNER),
                Word("Sister", "Kız kardeş", Difficulty.BEGINNER),
                Word("Brother", "Erkek kardeş", Difficulty.BEGINNER),
                Word("Grandmother", "Büyükanne", Difficulty.BEGINNER),
                Word("Grandfather", "Büyükbaba", Difficulty.BEGINNER),
                Word("Aunt", "Teyze", Difficulty.BEGINNER),
                Word("Uncle", "Amca", Difficulty.BEGINNER),
                Word("Cousin", "Kuzen", Difficulty.BEGINNER),
                Word("Child", "Çocuk", Difficulty.BEGINNER),
                Word("Son", "Oğul", Difficulty.BEGINNER),
                Word("Daughter", "Kız", Difficulty.BEGINNER),
                Word("Wife", "Eş", Difficulty.BEGINNER),
                Word("Husband", "Koca", Difficulty.BEGINNER),
                Word("Parents", "Ebeveynler", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "food_drinks",
            name = "Food & Drinks",
            description = "Common foods and beverages",
            difficulty = Difficulty.BEGINNER,
            iconName = "food",
            order = 5,
            words = listOf(
                Word("Water", "Su", Difficulty.BEGINNER),
                Word("Bread", "Ekmek", Difficulty.BEGINNER),
                Word("Milk", "Süt", Difficulty.BEGINNER),
                Word("Tea", "Çay", Difficulty.BEGINNER),
                Word("Coffee", "Kahve", Difficulty.BEGINNER),
                Word("Apple", "Elma", Difficulty.BEGINNER),
                Word("Orange", "Portakal", Difficulty.BEGINNER),
                Word("Chicken", "Tavuk", Difficulty.BEGINNER),
                Word("Meat", "Et", Difficulty.BEGINNER),
                Word("Fish", "Balık", Difficulty.BEGINNER),
                Word("Rice", "Pilav", Difficulty.BEGINNER),
                Word("Soup", "Çorba", Difficulty.BEGINNER),
                Word("Vegetable", "Sebze", Difficulty.BEGINNER),
                Word("Fruit", "Meyve", Difficulty.BEGINNER),
                Word("Juice", "Meyve suyu", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "time",
            name = "Time & Calendar",
            description = "Days, months, and time expressions",
            difficulty = Difficulty.BEGINNER,
            iconName = "calendar",
            order = 6,
            words = listOf(
                Word("Monday", "Pazartesi", Difficulty.BEGINNER),
                Word("Tuesday", "Salı", Difficulty.BEGINNER),
                Word("Wednesday", "Çarşamba", Difficulty.BEGINNER),
                Word("Thursday", "Perşembe", Difficulty.BEGINNER),
                Word("Friday", "Cuma", Difficulty.BEGINNER),
                Word("Saturday", "Cumartesi", Difficulty.BEGINNER),
                Word("Sunday", "Pazar", Difficulty.BEGINNER),
                Word("Today", "Bugün", Difficulty.BEGINNER),
                Word("Tomorrow", "Yarın", Difficulty.BEGINNER),
                Word("Yesterday", "Dün", Difficulty.BEGINNER),
                Word("Week", "Hafta", Difficulty.BEGINNER),
                Word("Month", "Ay", Difficulty.BEGINNER),
                Word("Year", "Yıl", Difficulty.BEGINNER),
                Word("Hour", "Saat", Difficulty.BEGINNER),
                Word("Minute", "Dakika", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "weather",
            name = "Weather & Seasons",
            description = "Weather conditions and seasons",
            difficulty = Difficulty.BEGINNER,
            iconName = "weather",
            order = 7,
            words = listOf(
                Word("Hot", "Sıcak", Difficulty.BEGINNER),
                Word("Cold", "Soğuk", Difficulty.BEGINNER),
                Word("Sunny", "Güneşli", Difficulty.BEGINNER),
                Word("Rainy", "Yağmurlu", Difficulty.BEGINNER),
                Word("Cloudy", "Bulutlu", Difficulty.BEGINNER),
                Word("Windy", "Rüzgarlı", Difficulty.BEGINNER),
                Word("Snow", "Kar", Difficulty.BEGINNER),
                Word("Storm", "Fırtına", Difficulty.BEGINNER),
                Word("Spring", "İlkbahar", Difficulty.BEGINNER),
                Word("Summer", "Yaz", Difficulty.BEGINNER),
                Word("Autumn", "Sonbahar", Difficulty.BEGINNER),
                Word("Winter", "Kış", Difficulty.BEGINNER),
                Word("Temperature", "Sıcaklık", Difficulty.BEGINNER),
                Word("Humidity", "Nem", Difficulty.BEGINNER),
                Word("Forecast", "Hava durumu", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "body_parts",
            name = "Body Parts",
            description = "Human body parts",
            difficulty = Difficulty.BEGINNER,
            iconName = "body",
            order = 8,
            words = listOf(
                Word("Head", "Baş", Difficulty.BEGINNER),
                Word("Eye", "Göz", Difficulty.BEGINNER),
                Word("Nose", "Burun", Difficulty.BEGINNER),
                Word("Mouth", "Ağız", Difficulty.BEGINNER),
                Word("Ear", "Kulak", Difficulty.BEGINNER),
                Word("Hand", "El", Difficulty.BEGINNER),
                Word("Arm", "Kol", Difficulty.BEGINNER),
                Word("Leg", "Bacak", Difficulty.BEGINNER),
                Word("Foot", "Ayak", Difficulty.BEGINNER),
                Word("Heart", "Kalp", Difficulty.BEGINNER),
                Word("Hair", "Saç", Difficulty.BEGINNER),
                Word("Finger", "Parmak", Difficulty.BEGINNER),
                Word("Neck", "Boyun", Difficulty.BEGINNER),
                Word("Shoulder", "Omuz", Difficulty.BEGINNER),
                Word("Back", "Sırt", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "common_objects",
            name = "Common Objects",
            description = "Everyday items",
            difficulty = Difficulty.BEGINNER,
            iconName = "objects",
            order = 9,
            words = listOf(
                Word("Phone", "Telefon", Difficulty.BEGINNER),
                Word("Book", "Kitap", Difficulty.BEGINNER),
                Word("Pen", "Kalem", Difficulty.BEGINNER),
                Word("Table", "Masa", Difficulty.BEGINNER),
                Word("Chair", "Sandalye", Difficulty.BEGINNER),
                Word("Door", "Kapı", Difficulty.BEGINNER),
                Word("Window", "Pencere", Difficulty.BEGINNER),
                Word("Bed", "Yatak", Difficulty.BEGINNER),
                Word("Computer", "Bilgisayar", Difficulty.BEGINNER),
                Word("Bag", "Çanta", Difficulty.BEGINNER),
                Word("Keys", "Anahtarlar", Difficulty.BEGINNER),
                Word("Wallet", "Cüzdan", Difficulty.BEGINNER),
                Word("Clock", "Saat", Difficulty.BEGINNER),
                Word("Mirror", "Ayna", Difficulty.BEGINNER),
                Word("Lamp", "Lamba", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "animals",
            name = "Animals",
            description = "Common animals",
            difficulty = Difficulty.BEGINNER,
            iconName = "animals",
            order = 10,
            words = listOf(
                Word("Dog", "Köpek", Difficulty.BEGINNER),
                Word("Cat", "Kedi", Difficulty.BEGINNER),
                Word("Bird", "Kuş", Difficulty.BEGINNER),
                Word("Fish", "Balık", Difficulty.BEGINNER),
                Word("Horse", "At", Difficulty.BEGINNER),
                Word("Cow", "İnek", Difficulty.BEGINNER),
                Word("Sheep", "Koyun", Difficulty.BEGINNER),
                Word("Chicken", "Tavuk", Difficulty.BEGINNER),
                Word("Lion", "Aslan", Difficulty.BEGINNER),
                Word("Tiger", "Kaplan", Difficulty.BEGINNER),
                Word("Elephant", "Fil", Difficulty.BEGINNER),
                Word("Monkey", "Maymun", Difficulty.BEGINNER),
                Word("Bear", "Ayı", Difficulty.BEGINNER),
                Word("Snake", "Yılan", Difficulty.BEGINNER),
                Word("Rabbit", "Tavşan", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "clothing",
            name = languageManager.getString("clothing"),
            description = languageManager.getString("clothing_desc"),
            difficulty = Difficulty.BEGINNER,
            iconName = "clothing",
            order = 11,
            words = listOf(
                Word("shirt", "gömlek", Difficulty.BEGINNER),
                Word("pants", "pantolon", Difficulty.BEGINNER),
                Word("shoes", "ayakkabı", Difficulty.BEGINNER),
                Word("dress", "elbise", Difficulty.BEGINNER),
                Word("hat", "şapka", Difficulty.BEGINNER),
                Word("socks", "çorap", Difficulty.BEGINNER),
                Word("jacket", "ceket", Difficulty.BEGINNER),
                Word("skirt", "etek", Difficulty.BEGINNER),
                Word("sweater", "kazak", Difficulty.BEGINNER),
                Word("scarf", "eşarp", Difficulty.BEGINNER),
                Word("gloves", "eldiven", Difficulty.BEGINNER),
                Word("belt", "kemer", Difficulty.BEGINNER),
                Word("tie", "kravat", Difficulty.BEGINNER),
                Word("coat", "palto", Difficulty.BEGINNER),
                Word("pajamas", "pijama", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "professions",
            name = languageManager.getString("professions"),
            description = languageManager.getString("professions_desc"),
            difficulty = Difficulty.BEGINNER,
            iconName = "work",
            order = 12,
            words = listOf(
                Word("teacher", "öğretmen", Difficulty.BEGINNER),
                Word("doctor", "doktor", Difficulty.BEGINNER),
                Word("engineer", "mühendis", Difficulty.BEGINNER),
                Word("chef", "aşçı", Difficulty.BEGINNER),
                Word("driver", "şoför", Difficulty.BEGINNER),
                Word("nurse", "hemşire", Difficulty.BEGINNER),
                Word("police officer", "polis", Difficulty.BEGINNER),
                Word("artist", "sanatçı", Difficulty.BEGINNER),
                Word("musician", "müzisyen", Difficulty.BEGINNER),
                Word("student", "öğrenci", Difficulty.BEGINNER),
                Word("worker", "işçi", Difficulty.BEGINNER),
                Word("farmer", "çiftçi", Difficulty.BEGINNER),
                Word("dentist", "diş hekimi", Difficulty.BEGINNER),
                Word("lawyer", "avukat", Difficulty.BEGINNER),
                Word("secretary", "sekreter", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "school_items",
            name = languageManager.getString("school_items"),
            description = languageManager.getString("school_items_desc"),
            difficulty = Difficulty.BEGINNER,
            iconName = "school",
            order = 13,
            words = listOf(
                Word("pencil", "kurşun kalem", Difficulty.BEGINNER),
                Word("pen", "tükenmez kalem", Difficulty.BEGINNER),
                Word("notebook", "defter", Difficulty.BEGINNER),
                Word("eraser", "silgi", Difficulty.BEGINNER),
                Word("ruler", "cetvel", Difficulty.BEGINNER),
                Word("backpack", "sırt çantası", Difficulty.BEGINNER),
                Word("scissors", "makas", Difficulty.BEGINNER),
                Word("glue", "yapıştırıcı", Difficulty.BEGINNER),
                Word("calculator", "hesap makinesi", Difficulty.BEGINNER),
                Word("textbook", "ders kitabı", Difficulty.BEGINNER),
                Word("pencil case", "kalem kutusu", Difficulty.BEGINNER),
                Word("sharpener", "kalemtraş", Difficulty.BEGINNER),
                Word("dictionary", "sözlük", Difficulty.BEGINNER),
                Word("folder", "dosya", Difficulty.BEGINNER),
                Word("paper", "kağıt", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "transportation",
            name = languageManager.getString("transportation"),
            description = languageManager.getString("transportation_desc"),
            difficulty = Difficulty.BEGINNER,
            iconName = "transport",
            order = 14,
            words = listOf(
                Word("car", "araba", Difficulty.BEGINNER),
                Word("bus", "otobüs", Difficulty.BEGINNER),
                Word("train", "tren", Difficulty.BEGINNER),
                Word("airplane", "uçak", Difficulty.BEGINNER),
                Word("bicycle", "bisiklet", Difficulty.BEGINNER),
                Word("motorcycle", "motosiklet", Difficulty.BEGINNER),
                Word("ship", "gemi", Difficulty.BEGINNER),
                Word("taxi", "taksi", Difficulty.BEGINNER),
                Word("subway", "metro", Difficulty.BEGINNER),
                Word("boat", "tekne", Difficulty.BEGINNER),
                Word("helicopter", "helikopter", Difficulty.BEGINNER),
                Word("tram", "tramvay", Difficulty.BEGINNER),
                Word("van", "minibüs", Difficulty.BEGINNER),
                Word("truck", "kamyon", Difficulty.BEGINNER),
                Word("ferry", "feribot", Difficulty.BEGINNER)
            )
        ),
        Section(
            id = "sports_equipment",
            name = languageManager.getString("sports_equipment"),
            description = languageManager.getString("sports_equipment_desc"),
            difficulty = Difficulty.BEGINNER,
            iconName = "sports",
            order = 15,
            words = listOf(
                Word("ball", "top", Difficulty.BEGINNER),
                Word("racket", "raket", Difficulty.BEGINNER),
                Word("bat", "sopa", Difficulty.BEGINNER),
                Word("goal", "kale", Difficulty.BEGINNER),
                Word("net", "file", Difficulty.BEGINNER),
                Word("helmet", "kask", Difficulty.BEGINNER),
                Word("glove", "eldiven", Difficulty.BEGINNER),
                Word("skis", "kayak", Difficulty.BEGINNER),
                Word("skateboard", "kaykay", Difficulty.BEGINNER),
                Word("swimming pool", "yüzme havuzu", Difficulty.BEGINNER),
                Word("gym", "spor salonu", Difficulty.BEGINNER),
                Word("basketball hoop", "basketbol potası", Difficulty.BEGINNER),
                Word("tennis court", "tenis kortu", Difficulty.BEGINNER),
                Word("running track", "koşu pisti", Difficulty.BEGINNER),
                Word("whistle", "düdük", Difficulty.BEGINNER)
            )
        ),
        // ... continuing with more sections

        // INTERMEDIATE SECTIONS
        Section(
            id = "daily_conversations",
            name = languageManager.getString("daily_conversations"),
            description = languageManager.getString("daily_conversations_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "conversations",
            order = 1,
            words = listOf(
                Word("How was your day?", "Günün nasıl geçti?", Difficulty.INTERMEDIATE),
                Word("What do you do for work?", "Ne iş yapıyorsun?", Difficulty.INTERMEDIATE),
                Word("I'm learning Turkish", "Türkçe öğreniyorum", Difficulty.INTERMEDIATE),
                Word("Where do you live?", "Nerede yaşıyorsun?", Difficulty.INTERMEDIATE),
                Word("I like this place", "Bu yeri seviyorum", Difficulty.INTERMEDIATE),
                Word("Can you help me?", "Bana yardım edebilir misin?", Difficulty.INTERMEDIATE),
                Word("Of course", "Tabii ki", Difficulty.INTERMEDIATE),
                Word("I don't understand", "Anlamıyorum", Difficulty.INTERMEDIATE),
                Word("Can you speak slowly?", "Yavaş konuşabilir misin?", Difficulty.INTERMEDIATE),
                Word("What time is it?", "Saat kaç?", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "shopping_phrases",
            name = languageManager.getString("shopping_phrases"),
            description = languageManager.getString("shopping_phrases_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "shopping",
            order = 2,
            words = listOf(
                Word("How much is this?", "Bu ne kadar?", Difficulty.INTERMEDIATE),
                Word("It's too expensive", "Çok pahalı", Difficulty.INTERMEDIATE),
                Word("Do you have this in blue?", "Bunun mavisi var mı?", Difficulty.INTERMEDIATE),
                Word("I'll take it", "Bunu alacağım", Difficulty.INTERMEDIATE),
                Word("Can I try it on?", "Deneyebilir miyim?", Difficulty.INTERMEDIATE),
                Word("Where is the fitting room?", "Deneme kabini nerede?", Difficulty.INTERMEDIATE),
                Word("Do you accept credit cards?", "Kredi kartı kabul ediyor musunuz?", Difficulty.INTERMEDIATE),
                Word("Is there a discount?", "İndirim var mı?", Difficulty.INTERMEDIATE),
                Word("Receipt", "Fiş", Difficulty.INTERMEDIATE),
                Word("Change", "Para üstü", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "travel_phrases",
            name = languageManager.getString("travel_phrases"),
            description = languageManager.getString("travel_phrases_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "travel",
            order = 3,
            words = listOf(
                Word("Airport", "Havalimanı", Difficulty.INTERMEDIATE),
                Word("Could you show me the way?", "Yolu gösterebilir misiniz?", Difficulty.INTERMEDIATE),
                Word("I'm lost", "Kayboldum", Difficulty.INTERMEDIATE),
                Word("Train station", "Tren istasyonu", Difficulty.INTERMEDIATE),
                Word("One ticket please", "Bir bilet lütfen", Difficulty.INTERMEDIATE),
                Word("When is the next bus?", "Bir sonraki otobüs ne zaman?", Difficulty.INTERMEDIATE),
                Word("Hotel reservation", "Otel rezervasyonu", Difficulty.INTERMEDIATE),
                Word("Tourist information", "Turist bilgilendirme", Difficulty.INTERMEDIATE),
                Word("Passport", "Pasaport", Difficulty.INTERMEDIATE),
                Word("Baggage claim", "Bagaj teslim", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "restaurant_phrases",
            name = languageManager.getString("restaurant_phrases"),
            description = languageManager.getString("restaurant_phrases_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "restaurant",
            order = 4,
            words = listOf(
                Word("Menu please", "Menü lütfen", Difficulty.INTERMEDIATE),
                Word("I would like to order", "Sipariş vermek istiyorum", Difficulty.INTERMEDIATE),
                Word("The bill please", "Hesap lütfen", Difficulty.INTERMEDIATE),
                Word("Is this spicy?", "Bu acı mı?", Difficulty.INTERMEDIATE),
                Word("Vegetarian", "Vejetaryen", Difficulty.INTERMEDIATE),
                Word("Delicious", "Lezzetli", Difficulty.INTERMEDIATE),
                Word("Waiter/Waitress", "Garson", Difficulty.INTERMEDIATE),
                Word("Reservation", "Rezervasyon", Difficulty.INTERMEDIATE),
                Word("Table for two", "İki kişilik masa", Difficulty.INTERMEDIATE),
                Word("Water", "Su", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "hobbies_interests",
            name = languageManager.getString("hobbies_interests"),
            description = languageManager.getString("hobbies_interests_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "hobbies",
            order = 5,
            words = listOf(
                Word("Reading", "Okuma", Difficulty.INTERMEDIATE),
                Word("Swimming", "Yüzme", Difficulty.INTERMEDIATE),
                Word("Photography", "Fotoğrafçılık", Difficulty.INTERMEDIATE),
                Word("Painting", "Resim yapmak", Difficulty.INTERMEDIATE),
                Word("Gardening", "Bahçecilik", Difficulty.INTERMEDIATE),
                Word("Cooking", "Yemek yapmak", Difficulty.INTERMEDIATE),
                Word("Dancing", "Dans etmek", Difficulty.INTERMEDIATE),
                Word("Playing music", "Müzik çalmak", Difficulty.INTERMEDIATE),
                Word("Hiking", "Doğa yürüyüşü", Difficulty.INTERMEDIATE),
                Word("Cycling", "Bisiklet sürmek", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "emotions_feelings",
            name = languageManager.getString("emotions_feelings"),
            description = languageManager.getString("emotions_feelings_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "emotions",
            order = 6,
            words = listOf(
                Word("Happy", "Mutlu", Difficulty.INTERMEDIATE),
                Word("Sad", "Üzgün", Difficulty.INTERMEDIATE),
                Word("Angry", "Kızgın", Difficulty.INTERMEDIATE),
                Word("Excited", "Heyecanlı", Difficulty.INTERMEDIATE),
                Word("Tired", "Yorgun", Difficulty.INTERMEDIATE),
                Word("Surprised", "Şaşkın", Difficulty.INTERMEDIATE),
                Word("Worried", "Endişeli", Difficulty.INTERMEDIATE),
                Word("Relaxed", "Rahat", Difficulty.INTERMEDIATE),
                Word("Confused", "Kafası karışık", Difficulty.INTERMEDIATE),
                Word("Proud", "Gururlu", Difficulty.INTERMEDIATE)
            )
        ),

        // Additional INTERMEDIATE sections (7-12)
        Section(
            id = "weather_climate",
            name = languageManager.getString("weather_climate"),
            description = languageManager.getString("weather_climate_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "weather",
            order = 7,
            words = listOf(
                Word("It's raining", "Yağmur yağıyor", Difficulty.INTERMEDIATE),
                Word("It's sunny", "Güneşli", Difficulty.INTERMEDIATE),
                Word("It's cold", "Soğuk", Difficulty.INTERMEDIATE),
                Word("It's hot", "Sıcak", Difficulty.INTERMEDIATE),
                Word("Cloudy", "Bulutlu", Difficulty.INTERMEDIATE),
                Word("Windy", "Rüzgarlı", Difficulty.INTERMEDIATE),
                Word("Storm", "Fırtına", Difficulty.INTERMEDIATE),
                Word("Temperature", "Sıcaklık", Difficulty.INTERMEDIATE),
                Word("Forecast", "Hava tahmini", Difficulty.INTERMEDIATE),
                Word("Seasons", "Mevsimler", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "health_fitness",
            name = languageManager.getString("health_fitness"),
            description = languageManager.getString("health_fitness_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "health",
            order = 8,
            words = listOf(
                Word("Exercise", "Egzersiz", Difficulty.INTERMEDIATE),
                Word("Gym", "Spor salonu", Difficulty.INTERMEDIATE),
                Word("Healthy diet", "Sağlıklı beslenme", Difficulty.INTERMEDIATE),
                Word("Workout", "Antrenman", Difficulty.INTERMEDIATE),
                Word("Rest", "Dinlenme", Difficulty.INTERMEDIATE),
                Word("Stretching", "Esneme", Difficulty.INTERMEDIATE),
                Word("Running", "Koşu", Difficulty.INTERMEDIATE),
                Word("Strength training", "Kuvvet antrenmanı", Difficulty.INTERMEDIATE),
                Word("Nutrition", "Beslenme", Difficulty.INTERMEDIATE),
                Word("Wellness", "Sağlık", Difficulty.INTERMEDIATE)
            )
        ),

        // Additional INTERMEDIATE sections (10-12)
        Section(
            id = "entertainment",
            name = languageManager.getString("entertainment"),
            description = languageManager.getString("entertainment_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "entertainment",
            order = 9,
            words = listOf(
                Word("Movie theater", "Sinema", Difficulty.INTERMEDIATE),
                Word("Concert", "Konser", Difficulty.INTERMEDIATE),
                Word("Museum", "Müze", Difficulty.INTERMEDIATE),
                Word("Theater play", "Tiyatro oyunu", Difficulty.INTERMEDIATE),
                Word("Art gallery", "Sanat galerisi", Difficulty.INTERMEDIATE),
                Word("Amusement park", "Lunapark", Difficulty.INTERMEDIATE),
                Word("Zoo", "Hayvanat bahçesi", Difficulty.INTERMEDIATE),
                Word("Sports event", "Spor etkinliği", Difficulty.INTERMEDIATE),
                Word("Live music", "Canlı müzik", Difficulty.INTERMEDIATE),
                Word("Festival", "Festival", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "technology_daily",
            name = languageManager.getString("technology_daily"),
            description = languageManager.getString("technology_daily_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "tech_daily",
            order = 10,
            words = listOf(
                Word("Smartphone", "Akıllı telefon", Difficulty.INTERMEDIATE),
                Word("Laptop", "Dizüstü bilgisayar", Difficulty.INTERMEDIATE),
                Word("Charger", "Şarj cihazı", Difficulty.INTERMEDIATE),
                Word("Headphones", "Kulaklık", Difficulty.INTERMEDIATE),
                Word("Password", "Şifre", Difficulty.INTERMEDIATE),
                Word("Internet connection", "İnternet bağlantısı", Difficulty.INTERMEDIATE),
                Word("Update", "Güncelleme", Difficulty.INTERMEDIATE),
                Word("Download", "İndirmek", Difficulty.INTERMEDIATE),
                Word("Upload", "Yüklemek", Difficulty.INTERMEDIATE),
                Word("Settings", "Ayarlar", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "sports_fitness",
            name = languageManager.getString("sports_fitness"),
            description = languageManager.getString("sports_fitness_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "sports",
            order = 11,
            words = listOf(
                Word("Football", "Futbol", Difficulty.INTERMEDIATE),
                Word("Basketball", "Basketbol", Difficulty.INTERMEDIATE),
                Word("Tennis", "Tenis", Difficulty.INTERMEDIATE),
                Word("Swimming pool", "Yüzme havuzu", Difficulty.INTERMEDIATE),
                Word("Team", "Takım", Difficulty.INTERMEDIATE),
                Word("Score", "Skor", Difficulty.INTERMEDIATE),
                Word("Championship", "Şampiyonluk", Difficulty.INTERMEDIATE),
                Word("Player", "Oyuncu", Difficulty.INTERMEDIATE),
                Word("Coach", "Antrenör", Difficulty.INTERMEDIATE),
                Word("Competition", "Yarışma", Difficulty.INTERMEDIATE)
            )
        ),

        // Add these new sections to the intermediate sections
        Section(
            id = "office_workplace",
            name = languageManager.getString("office_workplace"),
            description = languageManager.getString("office_workplace_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "office",
            order = 12,
            words = listOf(
                Word("meeting room", "toplantı odası", Difficulty.INTERMEDIATE),
                Word("deadline", "son teslim tarihi", Difficulty.INTERMEDIATE),
                Word("presentation", "sunum", Difficulty.INTERMEDIATE),
                Word("colleague", "iş arkadaşı", Difficulty.INTERMEDIATE),
                Word("schedule", "program", Difficulty.INTERMEDIATE),
                Word("conference call", "konferans görüşmesi", Difficulty.INTERMEDIATE),
                Word("project manager", "proje yöneticisi", Difficulty.INTERMEDIATE),
                Word("department", "departman", Difficulty.INTERMEDIATE),
                Word("supervisor", "amir", Difficulty.INTERMEDIATE),
                Word("office supplies", "ofis malzemeleri", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "home_furniture",
            name = languageManager.getString("home_furniture"),
            description = languageManager.getString("home_furniture_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "furniture",
            order = 13,
            words = listOf(
                Word("living room", "oturma odası", Difficulty.INTERMEDIATE),
                Word("dining table", "yemek masası", Difficulty.INTERMEDIATE),
                Word("bookshelf", "kitaplık", Difficulty.INTERMEDIATE),
                Word("armchair", "koltuk", Difficulty.INTERMEDIATE),
                Word("curtains", "perdeler", Difficulty.INTERMEDIATE),
                Word("carpet", "halı", Difficulty.INTERMEDIATE),
                Word("wardrobe", "gardırop", Difficulty.INTERMEDIATE),
                Word("nightstand", "komodin", Difficulty.INTERMEDIATE),
                Word("coffee table", "sehpa", Difficulty.INTERMEDIATE),
                Word("kitchen counter", "mutfak tezgahı", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "personal_care",
            name = languageManager.getString("personal_care"),
            description = languageManager.getString("personal_care_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "care",
            order = 14,
            words = listOf(
                Word("toothbrush", "diş fırçası", Difficulty.INTERMEDIATE),
                Word("shampoo", "şampuan", Difficulty.INTERMEDIATE),
                Word("hair dryer", "saç kurutma makinesi", Difficulty.INTERMEDIATE),
                Word("deodorant", "deodorant", Difficulty.INTERMEDIATE),
                Word("moisturizer", "nemlendirici", Difficulty.INTERMEDIATE),
                Word("razor", "tıraş bıçağı", Difficulty.INTERMEDIATE),
                Word("dental floss", "diş ipi", Difficulty.INTERMEDIATE),
                Word("face wash", "yüz temizleyici", Difficulty.INTERMEDIATE),
                Word("sunscreen", "güneş kremi", Difficulty.INTERMEDIATE),
                Word("hand cream", "el kremi", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "social_situations",
            name = languageManager.getString("social_situations"),
            description = languageManager.getString("social_situations_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "social",
            order = 15,
            words = listOf(
                Word("birthday party", "doğum günü partisi", Difficulty.INTERMEDIATE),
                Word("wedding ceremony", "düğün töreni", Difficulty.INTERMEDIATE),
                Word("business meeting", "iş toplantısı", Difficulty.INTERMEDIATE),
                Word("family gathering", "aile toplantısı", Difficulty.INTERMEDIATE),
                Word("graduation ceremony", "mezuniyet töreni", Difficulty.INTERMEDIATE),
                Word("dinner party", "akşam yemeği daveti", Difficulty.INTERMEDIATE),
                Word("job interview", "iş görüşmesi", Difficulty.INTERMEDIATE),
                Word("social event", "sosyal etkinlik", Difficulty.INTERMEDIATE),
                Word("celebration", "kutlama", Difficulty.INTERMEDIATE),
                Word("farewell party", "veda partisi", Difficulty.INTERMEDIATE)
            )
        ),
        Section(
            id = "electronics_gadgets",
            name = languageManager.getString("electronics_gadgets"),
            description = languageManager.getString("electronics_gadgets_desc"),
            difficulty = Difficulty.INTERMEDIATE,
            iconName = "electronics",
            order = 16,
            words = listOf(
                Word("smart watch", "akıllı saat", Difficulty.INTERMEDIATE),
                Word("wireless earbuds", "kablosuz kulaklık", Difficulty.INTERMEDIATE),
                Word("power bank", "taşınabilir şarj cihazı", Difficulty.INTERMEDIATE),
                Word("tablet computer", "tablet bilgisayar", Difficulty.INTERMEDIATE),
                Word("digital camera", "dijital kamera", Difficulty.INTERMEDIATE),
                Word("gaming console", "oyun konsolu", Difficulty.INTERMEDIATE),
                Word("bluetooth speaker", "bluetooth hoparlör", Difficulty.INTERMEDIATE),
                Word("smart home device", "akıllı ev cihazı", Difficulty.INTERMEDIATE),
                Word("wireless router", "kablosuz yönlendirici", Difficulty.INTERMEDIATE),
                Word("external hard drive", "harici hard disk", Difficulty.INTERMEDIATE)
            )
        ),


        // ADVANCED SECTIONS
        Section(
            id = "business_turkish",
            name = languageManager.getString("business_turkish"),
            description = languageManager.getString("business_turkish_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "business",
            order = 1,
            words = listOf(
                Word("Meeting", "Toplantı", Difficulty.ADVANCED),
                Word("Presentation", "Sunum", Difficulty.ADVANCED),
                Word("Project", "Proje", Difficulty.ADVANCED),
                Word("Deadline", "Son teslim tarihi", Difficulty.ADVANCED),
                Word("Report", "Rapor", Difficulty.ADVANCED),
                Word("Budget", "Bütçe", Difficulty.ADVANCED),
                Word("Investment", "Yatırım", Difficulty.ADVANCED),
                Word("Contract", "Sözleşme", Difficulty.ADVANCED),
                Word("Negotiation", "Pazarlık", Difficulty.ADVANCED),
                Word("Partnership", "Ortaklık", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "medical_terms",
            name = languageManager.getString("medical_terms"),
            description = languageManager.getString("medical_terms_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "medical",
            order = 2,
            words = listOf(
                Word("Headache", "Baş ağrısı", Difficulty.ADVANCED),
                Word("Fever", "Ateş", Difficulty.ADVANCED),
                Word("Blood pressure", "Tansiyon", Difficulty.ADVANCED),
                Word("Prescription", "Reçete", Difficulty.ADVANCED),
                Word("Pharmacy", "Eczane", Difficulty.ADVANCED),
                Word("Emergency", "Acil durum", Difficulty.ADVANCED),
                Word("Hospital", "Hastane", Difficulty.ADVANCED),
                Word("Doctor", "Doktor", Difficulty.ADVANCED),
                Word("Treatment", "Tedavi", Difficulty.ADVANCED),
                Word("Appointment", "Randevu", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "technology",
            name = languageManager.getString("technology"),
            description = languageManager.getString("technology_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "technology",
            order = 3,
            words = listOf(
                Word("Computer", "Bilgisayar", Difficulty.ADVANCED),
                Word("Software", "Yazılım", Difficulty.ADVANCED),
                Word("Hardware", "Donanım", Difficulty.ADVANCED),
                Word("Internet", "İnternet", Difficulty.ADVANCED),
                Word("Database", "Veritabanı", Difficulty.ADVANCED),
                Word("Cloud computing", "Bulut bilişim", Difficulty.ADVANCED),
                Word("Artificial Intelligence", "Yapay Zeka", Difficulty.ADVANCED),
                Word("Programming", "Programlama", Difficulty.ADVANCED),
                Word("Network", "Ağ", Difficulty.ADVANCED),
                Word("Cybersecurity", "Siber güvenlik", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "legal_terms",
            name = languageManager.getString("legal_terms"),
            description = languageManager.getString("legal_terms_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "legal",
            order = 4,
            words = listOf(
                Word("Law", "Hukuk", Difficulty.ADVANCED),
                Word("Court", "Mahkeme", Difficulty.ADVANCED),
                Word("Judge", "Hakim", Difficulty.ADVANCED),
                Word("Lawyer", "Avukat", Difficulty.ADVANCED),
                Word("Evidence", "Delil", Difficulty.ADVANCED),
                Word("Witness", "Tanık", Difficulty.ADVANCED),
                Word("Rights", "Haklar", Difficulty.ADVANCED),
                Word("Contract", "Sözleşme", Difficulty.ADVANCED),
                Word("Lawsuit", "Dava", Difficulty.ADVANCED),
                Word("Verdict", "Karar", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "environment",
            name = languageManager.getString("environment"),
            description = languageManager.getString("environment_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "environment",
            order = 5,
            words = listOf(
                Word("Climate change", "İklim değişikliği", Difficulty.ADVANCED),
                Word("Renewable energy", "Yenilenebilir enerji", Difficulty.ADVANCED),
                Word("Sustainability", "Sürdürülebilirlik", Difficulty.ADVANCED),
                Word("Pollution", "Kirlilik", Difficulty.ADVANCED),
                Word("Recycling", "Geri dönüşüm", Difficulty.ADVANCED),
                Word("Ecosystem", "Ekosistem", Difficulty.ADVANCED),
                Word("Conservation", "Koruma", Difficulty.ADVANCED),
                Word("Biodiversity", "Biyoçeşitlilik", Difficulty.ADVANCED),
                Word("Global warming", "Küresel ısınma", Difficulty.ADVANCED),
                Word("Carbon footprint", "Karbon ayak izi", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "psychology",
            name = languageManager.getString("psychology"),
            description = languageManager.getString("psychology_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "psychology",
            order = 6,
            words = listOf(
                Word("Behavior", "Davranış", Difficulty.ADVANCED),
                Word("Personality", "Kişilik", Difficulty.ADVANCED),
                Word("Memory", "Hafıza", Difficulty.ADVANCED),
                Word("Emotion", "Duygu", Difficulty.ADVANCED),
                Word("Motivation", "Motivasyon", Difficulty.ADVANCED),
                Word("Anxiety", "Kaygı", Difficulty.ADVANCED),
                Word("Depression", "Depresyon", Difficulty.ADVANCED),
                Word("Therapy", "Terapi", Difficulty.ADVANCED),
                Word("Stress", "Stres", Difficulty.ADVANCED),
                Word("Mental health", "Ruh sağlığı", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "arts_culture",
            name = languageManager.getString("arts_culture"),
            description = languageManager.getString("arts_culture_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "arts",
            order = 7,
            words = listOf(
                Word("Contemporary art", "Çağdaş sanat", Difficulty.ADVANCED),
                Word("Exhibition", "Sergi", Difficulty.ADVANCED),
                Word("Sculpture", "Heykel", Difficulty.ADVANCED),
                Word("Heritage", "Miras", Difficulty.ADVANCED),
                Word("Tradition", "Gelenek", Difficulty.ADVANCED),
                Word("Festival", "Festival", Difficulty.ADVANCED),
                Word("Architecture", "Mimari", Difficulty.ADVANCED),
                Word("Performance", "Performans", Difficulty.ADVANCED),
                Word("Gallery", "Galeri", Difficulty.ADVANCED),
                Word("Cultural identity", "Kültürel kimlik", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "social_media",
            name = languageManager.getString("social_media"),
            description = languageManager.getString("social_media_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "social",
            order = 8,
            words = listOf(
                Word("Profile", "Profil", Difficulty.ADVANCED),
                Word("Share", "Paylaş", Difficulty.ADVANCED),
                Word("Follow", "Takip et", Difficulty.ADVANCED),
                Word("Comment", "Yorum", Difficulty.ADVANCED),
                Word("Like", "Beğeni", Difficulty.ADVANCED),
                Word("Post", "Gönderi", Difficulty.ADVANCED),
                Word("Story", "Hikaye", Difficulty.ADVANCED),
                Word("Direct message", "Direkt mesaj", Difficulty.ADVANCED),
                Word("Hashtag", "Etiket", Difficulty.ADVANCED),
                Word("Trending", "Gündem", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "science_nature",
            name = languageManager.getString("science_nature"),
            description = languageManager.getString("science_nature_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "science",
            order = 9,
            words = listOf(
                Word("Evolution", "Evrim", Difficulty.ADVANCED),
                Word("Species", "Tür", Difficulty.ADVANCED),
                Word("Habitat", "Yaşam alanı", Difficulty.ADVANCED),
                Word("Energy", "Enerji", Difficulty.ADVANCED),
                Word("Matter", "Madde", Difficulty.ADVANCED),
                Word("Force", "Kuvvet", Difficulty.ADVANCED),
                Word("Chemical reaction", "Kimyasal tepkime", Difficulty.ADVANCED),
                Word("Gravity", "Yerçekimi", Difficulty.ADVANCED),
                Word("Molecule", "Molekül", Difficulty.ADVANCED),
                Word("Atom", "Atom", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "politics",
            name = languageManager.getString("politics"),
            description = languageManager.getString("politics_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "politics",
            order = 10,
            words = listOf(
                Word("Government", "Hükümet", Difficulty.ADVANCED),
                Word("Parliament", "Meclis", Difficulty.ADVANCED),
                Word("Election", "Seçim", Difficulty.ADVANCED),
                Word("Democracy", "Demokrasi", Difficulty.ADVANCED),
                Word("Political party", "Siyasi parti", Difficulty.ADVANCED),
                Word("Constitution", "Anayasa", Difficulty.ADVANCED),
                Word("Legislation", "Yasama", Difficulty.ADVANCED),
                Word("Opposition", "Muhalefet", Difficulty.ADVANCED),
                Word("Cabinet", "Kabine", Difficulty.ADVANCED),
                Word("Referendum", "Referandum", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "media_journalism",
            name = languageManager.getString("media_journalism"),
            description = languageManager.getString("media_journalism_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "media",
            order = 11,
            words = listOf(
                Word("Newspaper", "Gazete", Difficulty.ADVANCED),
                Word("Broadcast", "Yayın", Difficulty.ADVANCED),
                Word("Interview", "Röportaj", Difficulty.ADVANCED),
                Word("Editorial", "Başyazı", Difficulty.ADVANCED),
                Word("Press release", "Basın bildirisi", Difficulty.ADVANCED),
                Word("Breaking news", "Son dakika", Difficulty.ADVANCED),
                Word("Journalist", "Gazeteci", Difficulty.ADVANCED),
                Word("Media coverage", "Medya kapsamı", Difficulty.ADVANCED),
                Word("Source", "Kaynak", Difficulty.ADVANCED),
                Word("Publication", "Yayın", Difficulty.ADVANCED)
            )
        ),
        // Add these new sections to the advanced sections
        Section(
            id = "astronomy_space",
            name = languageManager.getString("astronomy_space"),
            description = languageManager.getString("astronomy_space_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "astronomy",
            order = 12,
            words = listOf(
                Word("galaxy", "galaksi", Difficulty.ADVANCED),
                Word("constellation", "takımyıldız", Difficulty.ADVANCED),
                Word("black hole", "kara delik", Difficulty.ADVANCED),
                Word("solar system", "güneş sistemi", Difficulty.ADVANCED),
                Word("telescope", "teleskop", Difficulty.ADVANCED),
                Word("nebula", "bulutsu", Difficulty.ADVANCED),
                Word("supernova", "süpernova", Difficulty.ADVANCED),
                Word("asteroid", "asteroit", Difficulty.ADVANCED),
                Word("space station", "uzay istasyonu", Difficulty.ADVANCED),
                Word("gravitational field", "çekim alanı", Difficulty.ADVANCED)
            )
        ),
        Section(
            id = "mythology_folklore",
            name = languageManager.getString("mythology_folklore"),
            description = languageManager.getString("mythology_folklore_desc"),
            difficulty = Difficulty.ADVANCED,
            iconName = "mythology",
            order = 13,
            words = listOf(
                Word("mythology", "mitoloji", Difficulty.ADVANCED),
                Word("legend", "efsane", Difficulty.ADVANCED),
                Word("ancient gods", "antik tanrılar", Difficulty.ADVANCED),
                Word("folklore", "halk bilimi", Difficulty.ADVANCED),
                Word("mythical creature", "mitolojik yaratık", Difficulty.ADVANCED),
                Word("epic tale", "destansı hikaye", Difficulty.ADVANCED),
                Word("hero's journey", "kahramanın yolculuğu", Difficulty.ADVANCED),
                Word("sacred text", "kutsal metin", Difficulty.ADVANCED),
                Word("ritual", "ritüel", Difficulty.ADVANCED),
                Word("cultural heritage", "kültürel miras", Difficulty.ADVANCED)
            )
        ),
        // ... continue with the remaining advanced sections and then the expert sections

        // EXPERT sections with proper translations
        Section(
            id = "philosophy_ethics",
            name = languageManager.getString("philosophy_ethics"),
            description = languageManager.getString("philosophy_ethics_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "philosophy",
            order = 1,
            words = listOf(
                Word("Moral philosophy", "Ahlak felsefesi", Difficulty.EXPERT),
                Word("Epistemology", "Bilgi kuramı", Difficulty.EXPERT),
                Word("Metaphysics", "Metafizik", Difficulty.EXPERT),
                Word("Existentialism", "Varoluşçuluk", Difficulty.EXPERT),
                Word("Phenomenology", "Fenomenoloji", Difficulty.EXPERT),
                Word("Rationalism", "Akılcılık", Difficulty.EXPERT),
                Word("Empiricism", "Deneycilik", Difficulty.EXPERT),
                Word("Dialectics", "Diyalektik", Difficulty.EXPERT),
                Word("Determinism", "Belirlenimcilik", Difficulty.EXPERT),
                Word("Free will", "Özgür irade", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "quantum_physics",
            name = languageManager.getString("quantum_physics"),
            description = languageManager.getString("quantum_physics_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "quantum",
            order = 2,
            words = listOf(
                Word("Quantum mechanics", "Kuantum mekaniği", Difficulty.EXPERT),
                Word("Wave function", "Dalga fonksiyonu", Difficulty.EXPERT),
                Word("Superposition", "Süperpozisyon", Difficulty.EXPERT),
                Word("Entanglement", "Dolanıklık", Difficulty.EXPERT),
                Word("Uncertainty principle", "Belirsizlik ilkesi", Difficulty.EXPERT),
                Word("Quantum field", "Kuantum alanı", Difficulty.EXPERT),
                Word("Wave-particle duality", "Dalga-parçacık ikiliği", Difficulty.EXPERT),
                Word("Quantum state", "Kuantum durumu", Difficulty.EXPERT),
                Word("Observable", "Gözlenebilir", Difficulty.EXPERT),
                Word("Quantum tunneling", "Kuantum tünelleme", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "neuroscience",
            name = languageManager.getString("neuroscience"),
            description = languageManager.getString("neuroscience_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "brain",
            order = 3,
            words = listOf(
                Word("Neural network", "Sinir ağı", Difficulty.EXPERT),
                Word("Synaptic plasticity", "Sinaptik plastisite", Difficulty.EXPERT),
                Word("Neurotransmitter", "Nörotransmitter", Difficulty.EXPERT),
                Word("Brain mapping", "Beyin haritalama", Difficulty.EXPERT),
                Word("Cognitive function", "Bilişsel işlev", Difficulty.EXPERT),
                Word("Neural pathway", "Sinirsel yolak", Difficulty.EXPERT),
                Word("Neuroplasticity", "Nöroplastisite", Difficulty.EXPERT),
                Word("Cerebral cortex", "Serebral korteks", Difficulty.EXPERT),
                Word("Neurodegenerative", "Nörodejeneratif", Difficulty.EXPERT),
                Word("Brain-computer interface", "Beyin-bilgisayar arayüzü", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "literature",
            name = "Literature",
            description = "Literary terms",
            difficulty = Difficulty.EXPERT,
            iconName = "literature",
            order = 4,
            words = listOf(
                Word("Literary criticism", "Edebi eleştiri", Difficulty.EXPERT),
                Word("Postmodernism", "Postmodernizm", Difficulty.EXPERT),
                Word("Metaphor", "Metafor", Difficulty.EXPERT),
                Word("Narrative structure", "Anlatı yapısı", Difficulty.EXPERT),
                Word("Intertextuality", "Metinlerarasılık", Difficulty.EXPERT),
                Word("Deconstruction", "Yapısöküm", Difficulty.EXPERT),
                Word("Stream of consciousness", "Bilinç akışı", Difficulty.EXPERT),
                Word("Hermeneutics", "Hermenötik", Difficulty.EXPERT),
                Word("Semiotics", "Göstergebilim", Difficulty.EXPERT),
                Word("Literary theory", "Edebiyat teorisi", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "academic_turkish",
            name = "Academic Turkish",
            description = "Academic terminology",
            difficulty = Difficulty.EXPERT,
            iconName = "academic",
            order = 5,
            words = listOf(
                Word("Methodology", "Metodoloji", Difficulty.EXPERT),
                Word("Hypothesis", "Hipotez", Difficulty.EXPERT),
                Word("Analysis", "Analiz", Difficulty.EXPERT),
                Word("Synthesis", "Sentez", Difficulty.EXPERT),
                Word("Dissertation", "Tez", Difficulty.EXPERT),
                Word("Citation", "Alıntı", Difficulty.EXPERT),
                Word("Peer review", "Hakem değerlendirmesi", Difficulty.EXPERT),
                Word("Abstract", "Özet", Difficulty.EXPERT),
                Word("Bibliography", "Kaynakça", Difficulty.EXPERT),
                Word("Research design", "Araştırma tasarımı", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "scientific_research",
            name = "Scientific Research",
            description = languageManager.getString("scientific_research_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "science",
            order = 6,
            words = listOf(
                Word("Empirical research", "Ampirik araştırma", Difficulty.EXPERT),
                Word("Data analysis", "Veri analizi", Difficulty.EXPERT),
                Word("Statistical significance", "İstatistiksel anlamlılık", Difficulty.EXPERT),
                Word("Control group", "Kontrol grubu", Difficulty.EXPERT),
                Word("Variable", "Değişken", Difficulty.EXPERT),
                Word("Correlation", "Korelasyon", Difficulty.EXPERT),
                Word("Experimental design", "Deneysel tasarım", Difficulty.EXPERT),
                Word("Qualitative research", "Nitel araştırma", Difficulty.EXPERT),
                Word("Quantitative research", "Nicel araştırma", Difficulty.EXPERT),
                Word("Research ethics", "Araştırma etiği", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "economics",
            name = languageManager.getString("economics"),
            description = languageManager.getString("economics_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "economics",
            order = 7,
            words = listOf(
                Word("Macroeconomics", "Makroekonomi", Difficulty.EXPERT),
                Word("Microeconomics", "Mikroekonomi", Difficulty.EXPERT),
                Word("Supply and demand", "Arz ve talep", Difficulty.EXPERT),
                Word("Inflation", "Enflasyon", Difficulty.EXPERT),
                Word("Fiscal policy", "Maliye politikası", Difficulty.EXPERT),
                Word("Monetary policy", "Para politikası", Difficulty.EXPERT),
                Word("Market equilibrium", "Piyasa dengesi", Difficulty.EXPERT),
                Word("Economic growth", "Ekonomik büyüme", Difficulty.EXPERT),
                Word("Trade deficit", "Dış ticaret açığı", Difficulty.EXPERT),
                Word("Exchange rate", "Döviz kuru", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "international_relations",
            name = languageManager.getString("international_relations"),
            description = languageManager.getString("international_relations_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "diplomacy",
            order = 8,
            words = listOf(
                Word("Diplomacy", "Diplomasi", Difficulty.EXPERT),
                Word("Foreign policy", "Dış politika", Difficulty.EXPERT),
                Word("International law", "Uluslararası hukuk", Difficulty.EXPERT),
                Word("Sovereignty", "Egemenlik", Difficulty.EXPERT),
                Word("Global governance", "Küresel yönetişim", Difficulty.EXPERT),
                Word("Bilateral relations", "İkili ilişkiler", Difficulty.EXPERT),
                Word("International organization", "Uluslararası örgüt", Difficulty.EXPERT),
                Word("Peace negotiations", "Barış müzakereleri", Difficulty.EXPERT),
                Word("Strategic partnership", "Stratejik ortaklık", Difficulty.EXPERT),
                Word("International security", "Uluslararası güvenlik", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "law_justice",
            name = languageManager.getString("law_justice"),
            description = languageManager.getString("law_justice_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "law",
            order = 9,
            words = listOf(
                Word("Constitutional law", "Anayasa hukuku", Difficulty.EXPERT),
                Word("Criminal law", "Ceza hukuku", Difficulty.EXPERT),
                Word("Civil law", "Medeni hukuk", Difficulty.EXPERT),
                Word("Jurisprudence", "İçtihat", Difficulty.EXPERT),
                Word("Legal precedent", "Hukuki emsal", Difficulty.EXPERT),
                Word("Jurisdiction", "Yargı yetkisi", Difficulty.EXPERT),
                Word("Legal liability", "Hukuki sorumluluk", Difficulty.EXPERT),
                Word("Due process", "Hukuki süreç", Difficulty.EXPERT),
                Word("Judicial review", "Yargı denetimi", Difficulty.EXPERT),
                Word("Legal doctrine", "Hukuk doktrini", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "engineering",
            name = languageManager.getString("engineering"),
            description = languageManager.getString("engineering_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "engineering",
            order = 10,
            words = listOf(
                Word("Mechanical engineering", "Makine mühendisliği", Difficulty.EXPERT),
                Word("Electrical engineering", "Elektrik mühendisliği", Difficulty.EXPERT),
                Word("Civil engineering", "İnşaat mühendisliği", Difficulty.EXPERT),
                Word("Software engineering", "Yazılım mühendisliği", Difficulty.EXPERT),
                Word("System design", "Sistem tasarımı", Difficulty.EXPERT),
                Word("Technical analysis", "Teknik analiz", Difficulty.EXPERT),
                Word("Quality control", "Kalite kontrol", Difficulty.EXPERT),
                Word("Project management", "Proje yönetimi", Difficulty.EXPERT),
                Word("Structural integrity", "Yapısal bütünlük", Difficulty.EXPERT),
                Word("Engineering ethics", "Mühendislik etiği", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "finance_banking",
            name = languageManager.getString("finance_banking"),
            description = languageManager.getString("finance_banking_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "finance",
            order = 11,
            words = listOf(
                Word("Investment banking", "Yatırım bankacılığı", Difficulty.EXPERT),
                Word("Risk management", "Risk yönetimi", Difficulty.EXPERT),
                Word("Portfolio analysis", "Portföy analizi", Difficulty.EXPERT),
                Word("Financial derivatives", "Finansal türevler", Difficulty.EXPERT),
                Word("Asset valuation", "Varlık değerleme", Difficulty.EXPERT),
                Word("Capital markets", "Sermaye piyasaları", Difficulty.EXPERT),
                Word("Corporate finance", "Kurumsal finans", Difficulty.EXPERT),
                Word("Financial statement", "Mali tablo", Difficulty.EXPERT),
                Word("Market analysis", "Piyasa analizi", Difficulty.EXPERT),
                Word("Credit assessment", "Kredi değerlendirmesi", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "cryptography_security",
            name = languageManager.getString("cryptography_security"),
            description = languageManager.getString("cryptography_security_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "security",
            order = 12,
            words = listOf(
                Word("encryption", "şifreleme", Difficulty.EXPERT),
                Word("cryptographic key", "kriptografik anahtar", Difficulty.EXPERT),
                Word("authentication", "kimlik doğrulama", Difficulty.EXPERT),
                Word("digital signature", "dijital imza", Difficulty.EXPERT),
                Word("cybersecurity", "siber güvenlik", Difficulty.EXPERT),
                Word("firewall", "güvenlik duvarı", Difficulty.EXPERT),
                Word("malware", "kötücül yazılım", Difficulty.EXPERT),
                Word("data breach", "veri ihlali", Difficulty.EXPERT),
                Word("vulnerability", "güvenlik açığı", Difficulty.EXPERT),
                Word("zero-day exploit", "sıfır gün açığı", Difficulty.EXPERT)
            )
        ),
        Section(
            id = "biochemistry",
            name = languageManager.getString("biochemistry"),
            description = languageManager.getString("biochemistry_desc"),
            difficulty = Difficulty.EXPERT,
            iconName = "biochemistry",
            order = 13,
            words = listOf(
                Word("enzyme", "enzim", Difficulty.EXPERT),
                Word("protein synthesis", "protein sentezi", Difficulty.EXPERT),
                Word("cellular respiration", "hücresel solunum", Difficulty.EXPERT),
                Word("metabolic pathway", "metabolik yol", Difficulty.EXPERT),
                Word("amino acid", "amino asit", Difficulty.EXPERT),
                Word("genetic code", "genetik kod", Difficulty.EXPERT),
                Word("membrane transport", "zar taşınımı", Difficulty.EXPERT),
                Word("molecular biology", "moleküler biyoloji", Difficulty.EXPERT),
                Word("cell signaling", "hücre sinyalleşmesi", Difficulty.EXPERT),
                Word("biochemical reaction", "biyokimyasal tepkime", Difficulty.EXPERT)
            )
        )
    )

    private var sectionProgress = mutableMapOf<String, Float>()
    private var difficultyProgress = mutableMapOf<Difficulty, Float>()
    private var sectionUnlocked = mutableMapOf<String, Boolean>()

    init {
        loadSavedProgress()
    }

    private fun calculateLockStatus(section: Section): Boolean {
        return when {
            section.order == 1 -> false // First section always unlocked
            sectionUnlocked[section.id] == true -> false // If previously unlocked, stay unlocked
            else -> {
                // Check if this section was previously unlocked in preferences
                runBlocking {
                    val wasUnlocked = preferencesManager.getSectionUnlocked(section.id)
                    if (wasUnlocked) {
                        sectionUnlocked[section.id] = true
                        false // Section was previously unlocked
                    } else {
                        val previousSection = sections.find { 
                            it.difficulty == section.difficulty && it.order == section.order - 1 
                        }
                        val previousProgress = previousSection?.let { sectionProgress[it.id] ?: 0f } ?: 0f
                        
                        if (previousProgress >= 0.7f) {
                            // If unlocked for the first time, save it
                            scope.launch {
                                sectionUnlocked[section.id] = true
                                preferencesManager.saveSectionUnlocked(section.id, true)
                            }
                            false
                        } else {
                            true
                        }
                    }
                }
            }
        }
    }

    fun getSectionProgress(sectionId: String): Float {
        return sectionProgress[sectionId] ?: 0f
    }

    fun getDifficultyProgress(difficulty: Difficulty): Float {
        return difficultyProgress[difficulty] ?: runBlocking {
            // If not in memory, get from preferences
            val progress = preferencesManager.getLevelProgress(difficulty.name)
            difficultyProgress[difficulty] = progress
            progress
        }
    }

    fun updateSectionProgress(sectionId: String, progress: Float) {
        scope.launch {
            try {
                // Save section progress
                sectionProgress[sectionId] = progress
                preferencesManager.saveSectionProgress(sectionId, progress)

                // Update difficulty progress
                val section = sections.find { it.id == sectionId }
                section?.difficulty?.let { difficulty ->
                    // Get all sections for this difficulty
                    val difficultySections = sections.filter { it.difficulty == difficulty }
                    
                    // Calculate total progress
                    var totalProgress = 0f
                    difficultySections.forEach { section ->
                        totalProgress += sectionProgress[section.id] ?: 0f
                    }
                    
                    // Calculate average progress
                    val averageProgress = totalProgress / difficultySections.size
                    
                    // Save both in memory and preferences
                    difficultyProgress[difficulty] = averageProgress
                    preferencesManager.saveLevelProgress(difficulty.name, averageProgress)

                    // Update UI
                    val updatedSections = sections.map { s ->
                        s.copy(
                            progress = sectionProgress[s.id] ?: 0f,
                            isLocked = calculateLockStatus(s)
                        )
                    }
                    _sectionsFlow.value = updatedSections
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSectionsForDifficulty(difficulty: Difficulty): List<Section> {
        val difficultySections = sections.filter { it.difficulty == difficulty }
        
        return difficultySections.map { section ->
            val progress = sectionProgress[section.id] ?: 0f
            section.copy(
                progress = progress,
                isLocked = calculateLockStatus(section)  // Calculate lock status for categories
            )
        }
    }

    fun updateSections(newSections: List<Section>) {
        val updatedSections = newSections.map { section ->
            val progress = sectionProgress[section.id] ?: 0f
            section.copy(
                progress = progress,
                isLocked = calculateLockStatus(section)  // Maintain category locking
            )
        }
        _sectionsFlow.value = updatedSections
    }

    fun getWordsForSection(sectionId: String): List<Word> {
        return sections.find { it.id == sectionId }?.words ?: emptyList()
    }

    private fun loadSavedProgress() {
        scope.launch {
            // Load all difficulty progress
            Difficulty.values().forEach { difficulty ->
                val progress = preferencesManager.getLevelProgress(difficulty.name)
                difficultyProgress[difficulty] = progress
            }
            
            // Load all section progress
            sections.forEach { section ->
                val progress = preferencesManager.getSectionProgress(section.id)
                sectionProgress[section.id] = progress
            }
            
            // Update UI
            updateSections(sections)
        }
    }
}