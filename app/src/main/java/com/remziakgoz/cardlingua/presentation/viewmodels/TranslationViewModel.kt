package com.remziakgoz.cardlingua.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remziakgoz.cardlingua.data.repository.TranslationRepository
import com.remziakgoz.cardlingua.data.language.LanguageManager
import com.remziakgoz.cardlingua.domain.model.Difficulty
import com.remziakgoz.cardlingua.domain.model.Section
import com.remziakgoz.cardlingua.domain.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val repository: TranslationRepository,
    private val languageManager: LanguageManager
) : ViewModel() {
    var currentWordIndex = 0
    private var incorrectWords: MutableSet<Word> = mutableSetOf()
    private var currentDifficulty: Difficulty? = null
    private var usedWords: MutableSet<Word> = mutableSetOf()
    private var currentReviewWords: List<Word> = emptyList()

    // Add new properties to track performance
    private var correctAnswers: Int = 0
    private var totalAttempts: Int = 0
    var accuracy by mutableStateOf(0f)
        private set

    var words: List<Word> = emptyList()
    var currentWord: String? by mutableStateOf(null)
    var translatedWord: String? by mutableStateOf(null)
    var score by mutableIntStateOf(0)
    var isGameOver by mutableStateOf(false)
    var currentSection by mutableStateOf<Section?>(null)

    // Add weight for incorrect words to appear more frequently
    private val incorrectWordWeight = 3

    private var correctWords: MutableSet<Word> = mutableSetOf()

    // Expose sections flow
    val sectionsState = repository.sectionsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val currentLanguage = languageManager.currentLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "en"
    )

    // Add after other properties
    private var previousWords = mutableListOf<Word>()

    // Add this property
    var slideDirection by mutableStateOf(0) // -1 for left, 0 for none, 1 for right
        private set

    // Add these properties at the top with other properties
    private var pendingWord: Word? = null
    var isAnimatingBack by mutableStateOf(false)
        private set

    // Add this class at the top level to track word attempts
    private data class WordAttempt(
        val word: Word,
        val wasCorrect: Boolean
    )

    // Add this property to the ViewModel class
    private var previousAttempts = mutableListOf<WordAttempt>()

    // Add this map at the top of the class
    private val emojiMap = mapOf(
        // Fix mismatched or missing words from repository
        "book" to "📚",  // Was missing this word
        "happy" to "😊",  // From emotions section
        "sad" to "😢",
        "angry" to "😠",
        "excited" to "🤩",
        "tired" to "😫",
        "surprised" to "🙀",
        "worried" to "😟",
        "relaxed" to "😌",
        "confused" to "😕",
        "proud" to "🦚",

        // Fix mismatched keys to match exactly with the words in the codebase
        "keys" to "🔑",  // Was "key"
        "traffic lights" to "🚦",  // Was "traffic light"
        "swimming goggles" to "🥽",  // Was "goggles"
        "swimming pools" to "🏊",  // Was "swimming pool"
        "basketball hoops" to "🏀",  // Was "basketball hoop"
        "tennis courts" to "🎾",  // Was "tennis court"
        "running tracks" to "🏃",  // Was "running track"
        "pencil cases" to "🖊️",  // Was "pencil case"
        "text books" to "📚",  // Was "textbook"
        "white boards" to "📋",  // Was "whiteboard"
        "police cars" to "🚓",  // Was "police car"
        "fire trucks" to "🚒",  // Was "fire truck"
        "traffic lights" to "🚦",  // Was "traffic light"
        "yoga mats" to "🧘",  // Was "yoga mat"
        "bicycle helmets" to "⛑️",  // Was "bicycle helmet"
        "stop watches" to "⏱️",  // Was "stopwatch"
        "score boards" to "📊",  // Was "scoreboard"
        // Numbers (using filled/black circled numbers)
        "zero" to "⓿",
        "one" to "❶",
        "two" to "❷",
        "three" to "❸",
        "four" to "❹",
        "five" to "❺",
        "six" to "❻",
        "seven" to "❼",
        "eight" to "❽",
        "nine" to "❾",
        "ten" to "❿",
        "twenty" to "⓴",
        "thirty" to "㉚",
        "forty" to "㊵",
        "fifty" to "㊿",
        // Basic Greetings
        "hello" to "👋",
        "good morning" to "🌅",
        "good afternoon" to "☀️",
        "good evening" to "🌆",
        "good night" to "🌙",
        "goodbye" to "👋",
        "see you later" to "👋",
        "how are you?" to "🤔",
        "i'm fine" to "😊",
        "thank you" to "🙏",
        "you're welcome" to "😊",
        "please" to "🙏",
        "nice to meet you" to "🤝",
        "what's your name?" to "🤔",
        "my name is..." to "📛",
        "excuse me" to "🙋",
        "good day" to "🌞",
        "welcome" to "🤗",
        "bye bye" to "👋",
        "have a nice day" to "🌟",
        "good luck" to "🍀",
        "sorry" to "😔",
        "no problem" to "👍",
        // Colors
        "red" to "🔴",
        "blue" to "🔵",
        "green" to "🟢",
        "yellow" to "🟡",
        "black" to "⚫",
        "white" to "⚪",
        "orange" to "🟠",
        "purple" to "🟣",
        "pink" to "💗",
        "brown" to "🟤",
        "gray" to "◻️",
        "gold" to "🥇",
        "silver" to "🥈",
        "light" to "💡",
        "dark" to "🌑",
        "rainbow" to "🌈",
        "colorful" to "🎨",
        "bright" to "✨",
        "shiny" to "⭐",
        // Family Members
        "mother" to "👩",
        "father" to "👨",
        "sister" to "👧",
        "brother" to "👦",
        "grandmother" to "👵",
        "grandfather" to "👴",
        "aunt" to "👩",
        "uncle" to "👨",
        "cousin" to "🧑",
        "child" to "👶",
        "son" to "👦",
        "daughter" to "👧",
        "wife" to "👰",
        "husband" to "🤵",
        "parents" to "👫",
        "family" to "👨‍👩‍👧‍👦",
        "twins" to "👯",
        "baby" to "👶",
        "relatives" to "👥",
        "in-laws" to "👫",
        "stepmother" to "👩",
        "stepfather" to "👨",
        // Food & Drinks
        "water" to "💧",
        "bread" to "🍞",
        "milk" to "🥛",
        "tea" to "☕",
        "coffee" to "☕",
        "apple" to "🍎",
        "orange" to "🍊",
        "chicken" to "🍗",
        "meat" to "🥩",
        "fish" to "🐟",
        "rice" to "🍚",
        "soup" to "🥣",
        "egg" to "🥚",
        "cheese" to "🧀",
        "banana" to "🍌",
        "juice" to "🧃",
        "soda" to "🥤",
        "pizza" to "🍕",
        "hamburger" to "🍔",
        "sandwich" to "🥪",
        "salad" to "🥗",
        "fruit" to "🍎",
        "vegetable" to "🥕",
        "dessert" to "🍰",
        "snack" to "🍪",
        "breakfast" to "🍳",
        "lunch" to "🍽️",
        "dinner" to "🍖",
        "meal" to "🍱",
        // Animals
        "dog" to "🐕",
        "cat" to "🐱",
        "bird" to "🐦",
        "fish" to "🐟",
        "horse" to "🐎",
        "cow" to "🐄",
        "sheep" to "🐑",
        "chicken" to "🐔",
        "lion" to "🦁",
        "tiger" to "🐯",
        "elephant" to "🐘",
        "monkey" to "🐒",
        "bear" to "🐻",
        "snake" to "🐍",
        "rabbit" to "🐰",
        "penguin" to "🐧",
        "duck" to "🦆",
        "mouse" to "🐭",
        "hamster" to "🐹",
        "frog" to "🐸",
        "butterfly" to "🦋",
        "bee" to "🐝",
        "ant" to "🐜",
        "spider" to "🕷️",
        "pet" to "🐕",
        // Clothing
        "shirt" to "👕",
        "pants" to "👖",
        "shoes" to "👞",
        "dress" to "👗",
        "hat" to "🎩",
        "socks" to "🧦",
        "jacket" to "🧥",
        "skirt" to "👗",
        "sweater" to "🧥",
        "scarf" to "🧣",
        "gloves" to "🧤",
        "belt" to "👔",
        "tie" to "👔",
        "coat" to "🧥",
        "pajamas" to "🛏️",
        "boots" to "👢",
        "sandals" to "👡",
        "sneakers" to "👟",
        "umbrella" to "☔",
        "glasses" to "👓",
        "jewelry" to "💍",
        "watch" to "⌚",
        "ring" to "💍",
        "necklace" to "📿",
        "bracelet" to "⌚",
        "swimsuit" to "🩱",
        "uniform" to "👔",
        // School Items
        "pencil" to "✏️",
        "pen" to "🖊️",
        "notebook" to "📓",
        "eraser" to "🧹",
        "ruler" to "📏",
        "backpack" to "🎒",
        "scissors" to "✂️",
        "glue" to "🖌️",
        "calculator" to "🧮",
        "textbook" to "📚",
        "pencil case" to "🖊️",
        "sharpener" to "✏️",
        "dictionary" to "📖",
        "folder" to "📁",
        "paper" to "📄",
        "computer" to "💻",
        "tablet" to "📱",
        "whiteboard" to "📋",
        "marker" to "🖊️",
        "paint" to "🎨",
        "brush" to "🖌️",
        "desk" to "🪑",
        "chair" to "🪑",
        "clock" to "🕐",
        "calendar" to "📅",
        "map" to "🗺️",
        "globe" to "🌍",
        // Transportation
        "car" to "🚗",
        "bus" to "🚌",
        "train" to "🚂",
        "airplane" to "✈️",
        "bicycle" to "🚲",
        "motorcycle" to "🏍️",
        "ship" to "🚢",
        "taxi" to "🚕",
        "subway" to "🚇",
        "boat" to "⛵",
        "helicopter" to "🚁",
        "tram" to "🚊",
        "van" to "🚐",
        "truck" to "🚛",
        "ferry" to "⛴️",
        "scooter" to "🛴",
        "rocket" to "🚀",
        "ambulance" to "🚑",
        "police car" to "🚓",
        "fire truck" to "🚒",
        "traffic light" to "🚦",
        "station" to "🚉",
        "airport" to "✈️",
        "ticket" to "🎫",
        // Sports Equipment
        "ball" to "⚽",
        "racket" to "🎾",
        "bat" to "🏏",
        "goal" to "🥅",
        "net" to "🥅",
        "helmet" to "⛑️",
        "glove" to "🧤",
        "skis" to "🎿",
        "skateboard" to "🛹",
        "swimming pool" to "🏊",
        "gym" to "🏋️",
        "basketball hoop" to "🏀",
        "tennis court" to "🎾",
        "running track" to "🏃",
        "whistle" to "📢",
        "football" to "🏈",
        "basketball" to "🏀",
        "baseball" to "⚾",
        "tennis ball" to "🎾",
        "volleyball" to "🏐",
        "soccer ball" to "⚽",
        "medal" to "🏅",
        "trophy" to "🏆",
        "sports shoes" to "👟",
        "yoga mat" to "🧘",
        "dumbbell" to "🏋️",
        "bicycle helmet" to "⛑️",
        "stopwatch" to "⏱️",
        "scoreboard" to "📊",
        // Professions (updated to match repository exactly)
        "teacher" to "👩‍🏫",
        "doctor" to "👨‍⚕️",
        "engineer" to "👷",
        "chef" to "👨‍🍳",
        "driver" to "🚗",
        "nurse" to "👩‍⚕️",
        "police officer" to "👮",
        "artist" to "👨‍🎨",
        "musician" to "👨🏽‍🎤",
        "student" to "👨‍🎓",
        "worker" to "👷",
        "farmer" to "👨🏽‍🌾",
        "dentist" to "🦷",
        "lawyer" to "👨‍⚖️",
        "secretary" to "👩‍💼",
        // Time
        "monday" to "📅",
        "tuesday" to "📅",
        "wednesday" to "📅",
        "thursday" to "📅",
        "friday" to "📅",
        "saturday" to "📅",
        "sunday" to "📅",
        "morning" to "🌅",
        "afternoon" to "☀️",
        "evening" to "🌆",
        "night" to "🌙",
        "today" to "📅",
        "tomorrow" to "📆",
        "yesterday" to "⏪",
        "week" to "📅",
        "month" to "📅",
        "year" to "🗓️",
        "hour" to "🕐",
        "minute" to "⏰",
        "second" to "⏱️",
        "time" to "⌚",
        "day" to "📆",
        "weekend" to "🏖️",
        "schedule" to "📋",
        "early" to "🌄",
        "late" to "🌙",
        "now" to "⌛",
        // Weather & Seasons (updated to match repository exactly)
        "hot" to "🌡️",
        "cold" to "❄️",
        "sunny" to "☀️",
        "rainy" to "🌧️",
        "cloudy" to "☁️",
        "windy" to "💨",
        "snow" to "❄️",
        "storm" to "⛈️",
        "spring" to "🌱",
        "summer" to "☀️",
        "autumn" to "🍂",
        "winter" to "⛄",
        "temperature" to "🌡️",
        "humidity" to "💧",
        "forecast" to "📱",
        "seasons" to "🍂",
        "it's hot" to "🥵",
        "it's cold" to "❄️",
        "it's sunny" to "☀️",
        "it's raining" to "🌧️",

        // Body Parts (updated to match repository exactly)
        "head" to "👤",
        "eye" to "👁️",
        "nose" to "👃",
        "mouth" to "👄",
        "ear" to "👂",
        "hand" to "✋",
        "arm" to "💪",
        "leg" to "🦵",
        "foot" to "🦶",
        "heart" to "❤️",
        "hair" to "💇",
        "finger" to "👆",
        "neck" to "🧣",
        "shoulder" to "💪",
        "back" to "🔙",
        // Common Objects
        "phone" to "📱",
        "wallet" to "👛",
        "bag" to "🎒",
        "bottle" to "🍾",
        "glass" to "🥃",
        "cup" to "☕",
        "plate" to "🍽️",
        "mirror" to "🪞",
        "camera" to "📸",
        "television" to "📺",
        "radio" to "📻",
        "lamp" to "💡",
        "door" to "🚪",
        "window" to "🪟",
        "bed" to "🛏️",
        "chair" to "🪑",
        "table" to "🪑",
        "clock" to "🕐",
        "picture" to "🖼️",
        "box" to "📦",
        "gift" to "🎁",
        "soap" to "🧼",
        "toothbrush" to "🪥",
        "towel" to "🧻",
        // Daily Conversations
        "how was your day?" to "💭",
        "what do you do for work?" to "💼",
        "i'm learning turkish" to "📚",
        "where do you live?" to "🏠",
        "i like this place" to "😊",
        "can you help me?" to "🤝",
        "of course" to "👍",
        "i don't understand" to "🤔",
        "can you speak slowly?" to "🗣️",
        "what time is it?" to "⌚",
        // Shopping Phrases
        "how much is this?" to "💰",
        "it's too expensive" to "💸",
        "do you have this in blue?" to "🔵",
        "i'll take it" to "🛍️",
        "can i try it on?" to "👕",
        "where is the fitting room?" to "🚪",
        "do you accept credit cards?" to "💳",
        "is there a discount?" to "🏷️",
        "receipt" to "🧾",
        "change" to "💵",
        // Travel Phrases
        "airport" to "✈️",
        "could you show me the way?" to "🗺️",
        "i'm lost" to "❓",
        "train station" to "🚉",
        "one ticket please" to "🎫",
        "when is the next bus?" to "🚌",
        "hotel reservation" to "🏨",
        "tourist information" to "ℹ️",
        "passport" to "🛂",
        "baggage claim" to "🧳",
        // Restaurant Phrases
        "menu please" to "📖",
        "i would like to order" to "📝",
        "the bill please" to "💶",
        "is this spicy?" to "🌶️",
        "vegetarian" to "🥗",
        "delicious" to "😋",
        "waiter/waitress" to "🧑‍🍳",
        "reservation" to "📅",
        "table for two" to "👥",
        "water" to "💧",
        // Hobbies & Interests
        "reading" to "📚",
        "swimming" to "🏊",
        "photography" to "📸",
        "painting" to "🎨",
        "gardening" to "🌱",
        "cooking" to "👨‍🍳",
        "dancing" to "💃",
        "playing music" to "🎵",
        "hiking" to "🥾",
        "cycling" to "🚲",
        // Health & Fitness
        "exercise" to "🏃",
        "gym" to "💪",
        "healthy diet" to "🥬",
        "workout" to "🏋️",
        "rest" to "😴",
        "stretching" to "🧘",
        "running" to "🏃‍♂️",
        "strength training" to "🏋️‍♂️",
        "nutrition" to "🥑",
        "wellness" to "🧘‍♀️",
        // Entertainment
        "movie theater" to "🎬",
        "concert" to "🎵",
        "museum" to "🏛️",
        "theater play" to "🎭",
        "art gallery" to "🖼️",
        "amusement park" to "🎡",
        "zoo" to "🦁",
        "sports event" to "🏟️",
        "live music" to "🎸",
        "festival" to "🎪",
        // Technology Daily
        "smartphone" to "📱",
        "laptop" to "💻",
        "charger" to "🔌",
        "headphones" to "🎧",
        "password" to "🔑",
        "internet connection" to "📶",
        "update" to "🔄",
        "download" to "⬇️",
        "upload" to "⬆️",
        "settings" to "⚙️",
        // Sports & Fitness
        "football" to "⚽",
        "basketball" to "🏀",
        "tennis" to "🎾",
        "swimming pool" to "🏊",
        "team" to "👥",
        "score" to "📊",
        "championship" to "🏆",
        "player" to "🏃",
        "coach" to "👨‍🏫",
        "competition" to "🥇",
        // Office & Workplace
        "meeting room" to "👥",
        "deadline" to "⏰",
        "presentation" to "📊",
        "colleague" to "👔",
        "schedule" to "📅",
        "conference call" to "📞",
        "project manager" to "👨‍💼",
        "department" to "🏢",
        "supervisor" to "👨‍💼",
        "office supplies" to "📌",
        // Home & Furniture
        "living room" to "🛋️",
        "dining table" to "🪑",
        "bookshelf" to "📚",
        "armchair" to "💺",
        "curtains" to "🪟",
        "carpet" to "🏠",
        "wardrobe" to "👔",
        "nightstand" to "🛏️",
        "coffee table" to "☕",
        "kitchen counter" to "🍳",
        // Personal Care
        "toothbrush" to "🪥",
        "shampoo" to "🧴",
        "hair dryer" to "💨",
        "deodorant" to "🧴",
        "moisturizer" to "💦",
        "razor" to "🪒",
        "dental floss" to "🦷",
        "face wash" to "🧼",
        "sunscreen" to "☀️",
        "hand cream" to "🧴",
        // Social Situations
        "birthday party" to "🎂",
        "wedding ceremony" to "💒",
        "business meeting" to "💼",
        "family gathering" to "👨‍👩‍👧‍👦",
        "graduation ceremony" to "🎓",
        "dinner party" to "🍽️",
        "job interview" to "👔",
        "social event" to "🎉",
        "celebration" to "🎊",
        "farewell party" to "👋",
        // Electronics & Gadgets
        "smart watch" to "⌚",
        "wireless earbuds" to "🎧",
        "power bank" to "🔋",
        "tablet computer" to "📱",
        "digital camera" to "📸",
        "gaming console" to "🎮",
        "bluetooth speaker" to "🔊",
        "smart home device" to "🏠",
        "wireless router" to "📶",
        "external hard drive" to "💾",
        // Business English
        "meeting" to "👥",
        "presentation" to "📊",
        "project" to "📋",
        "deadline" to "⏰",
        "report" to "📉",
        "budget" to "💰",
        "investment" to "📈",
        "contract" to "📄",
        "negotiation" to "🤝",
        "partnership" to "🤝",
        // Medical Terms
        "headache" to "🤕",
        "fever" to "🤒",
        "blood pressure" to "💉",
        "prescription" to "📋",
        "pharmacy" to "💊",
        "emergency" to "🚨",
        "hospital" to "🏥",
        "doctor" to "👨‍⚕️",
        "treatment" to "💉",
        "appointment" to "📅",
        // Technology
        "computer" to "💻",
        "software" to "💿",
        "hardware" to "🔧",
        "internet" to "🌐",
        "database" to "💾",
        "cloud computing" to "☁️",
        "artificial intelligence" to "🤖",
        "programming" to "👨‍💻",
        "network" to "🕸️",
        "cybersecurity" to "🔒",
        // Legal Terms
        "law" to "⚖️",
        "court" to "🏛️",
        "judge" to "👨‍⚖️",
        "lawyer" to "💼",
        "evidence" to "🔍",
        "witness" to "🤥",
        "rights" to "📜",
        "contract" to "📝",
        "lawsuit" to "⚖️",
        "verdict" to "🔨",
        // Environment
        "climate change" to "🌡️",
        "renewable energy" to "♻️",
        "sustainability" to "🌱",
        "pollution" to "🏭",
        "recycling" to "♻️",
        "ecosystem" to "🌍",
        "conservation" to "🌿",
        "biodiversity" to "🦋",
        "global warming" to "🌡️",
        "carbon footprint" to "👣",
        // Psychology
        "behavior" to "🧠",
        "personality" to "😊",
        "memory" to "🤔",
        "emotion" to "😢",
        "motivation" to "💪",
        "anxiety" to "😰",
        "depression" to "😔",
        "therapy" to "🛋️",
        "stress" to "😫",
        "mental health" to "🧠",
        // Arts & Culture
        "contemporary art" to "🎨",
        "exhibition" to "🖼️",
        "sculpture" to "🗿",
        "heritage" to "🏛️",
        "tradition" to "👘",
        "festival" to "🎪",
        "architecture" to "🏛️",
        "performance" to "🎭",
        "gallery" to "🖼️",
        "cultural identity" to "🎭",
        // Social Media
        "profile" to "👤",
        "share" to "↗️",
        "follow" to "➡️",
        "comment" to "💬",
        "like" to "❤️",
        "post" to "📱",
        "story" to "📖",
        "direct message" to "📨",
        "hashtag" to "#️⃣",
        "trending" to "📈",
        // Science & Nature
        "evolution" to "🧬",
        "species" to "🦋",
        "habitat" to "🌳",
        "energy" to "⚡",
        "matter" to "🔮",
        "force" to "💫",
        "chemical reaction" to "🧪",
        "gravity" to "🌍",
        "molecule" to "⚛️",
        "atom" to "⚛️",
        // Politics
        "government" to "🏛️",
        "parliament" to "🏛️",
        "election" to "🗳️",
        "democracy" to "✌️",
        "political party" to "🏢",
        "constitution" to "📜",
        "legislation" to "📋",
        "opposition" to "🗣️",
        "cabinet" to "👥",
        "referendum" to "✍️",
        // Media & Journalism
        "newspaper" to "📰",
        "broadcast" to "📺",
        "interview" to "🎤",
        "editorial" to "📝",
        "press release" to "📢",
        "breaking news" to "⚡",
        "journalist" to "👨‍💼",
        "media coverage" to "📡",
        "source" to "📚",
        "publication" to "📖",
        // Astronomy & Space
        "galaxy" to "🌌",
        "constellation" to "⭐",
        "black hole" to "🕳️",
        "solar system" to "🌞",
        "telescope" to "🔭",
        "nebula" to "🌌",
        "supernova" to "💥",
        "asteroid" to "☄️",
        "space station" to "🛸",
        "gravitational field" to "🌍",
        // Mythology & Folklore
        "mythology" to "🧌",
        "legend" to "📖",
        "ancient gods" to "⚡",
        "folklore" to "🎭",
        "mythical creature" to "🐉",
        "epic tale" to "📚",
        "hero's journey" to "⚔️",
        "sacred text" to "📜",
        "ritual" to "🕯️",
        "cultural heritage" to "🏛️",
        // Philosophy & Ethics
        "moral philosophy" to "🤔",
        "epistemology" to "🧠",
        "metaphysics" to "🌌",
        "existentialism" to "🤷",
        "phenomenology" to "👁️",
        "rationalism" to "💭",
        "empiricism" to "🔬",
        "dialectics" to "⚖️",
        "determinism" to "🎯",
        "free will" to "🦋",
        // Quantum Physics
        "quantum mechanics" to "⚛️",
        "wave function" to "〰️",
        "superposition" to "🔄",
        "entanglement" to "🔀",
        "uncertainty principle" to "❓",
        "quantum field" to "🌐",
        "wave-particle duality" to "🔄",
        "quantum state" to "💫",
        "observable" to "👁️",
        "quantum tunneling" to "🚇",
        // Neuroscience
        "neural network" to "🧠",
        "synaptic plasticity" to "🔄",
        "neurotransmitter" to "⚡",
        "brain mapping" to "🗺️",
        "cognitive function" to "💭",
        "neural pathway" to "🛣️",
        "neuroplasticity" to "🔄",
        "cerebral cortex" to "🧠",
        "neurodegenerative" to "📉",
        "brain-computer interface" to "🤖",
        // Literature
        "literary criticism" to "📚",
        "postmodernism" to "🎭",
        "metaphor" to "🖋️",
        "narrative structure" to "📖",
        "intertextuality" to "🔄",
        "deconstruction" to "🔍",
        "stream of consciousness" to "💭",
        "hermeneutics" to "🔍",
        "semiotics" to "🔤",
        "literary theory" to "📚",
        // Academic Turkish
        "methodology" to "📊",
        "hypothesis" to "❓",
        "analysis" to "🔍",
        "synthesis" to "🔄",
        "dissertation" to "📑",
        "citation" to "💬",
        "peer review" to "👥",
        "abstract" to "📝",
        "bibliography" to "📚",
        "research design" to "🎯",
        // Scientific Research
        "empirical research" to "🔬",
        "data analysis" to "📊",
        "statistical significance" to "📈",
        "control group" to "⚖️",
        "variable" to "🔄",
        "correlation" to "🔗",
        "experimental design" to "🧪",
        "qualitative research" to "📝",
        "quantitative research" to "📊",
        "research ethics" to "⚖️",
        // Economics
        "macroeconomics" to "📈",
        "microeconomics" to "📉",
        "supply and demand" to "⚖️",
        "inflation" to "💹",
        "fiscal policy" to "💰",
        "monetary policy" to "🏦",
        "market equilibrium" to "⚖️",
        "economic growth" to "📈",
        "trade deficit" to "📊",
        "exchange rate" to "💱",
        // International Relations
        "diplomacy" to "🤝",
        "foreign policy" to "🌐",
        "international law" to "⚖️",
        "sovereignty" to "👑",
        "global governance" to "🌍",
        "bilateral relations" to "🤝",
        "international organization" to "🏛️",
        "peace negotiations" to "🕊️",
        "strategic partnership" to "🤝",
        "international security" to "🛡️",
        // Law & Justice
        "constitutional law" to "📜",
        "criminal law" to "⚖️",
        "civil law" to "👨‍⚖️",
        "jurisprudence" to "📚",
        "legal precedent" to "🔨",
        "jurisdiction" to "🏛️",
        "legal liability" to "⚖️",
        "due process" to "⚖️",
        "judicial review" to "🔍",
        "legal doctrine" to "📜",
        // Engineering
        "mechanical engineering" to "⚙️",
        "electrical engineering" to "⚡",
        "civil engineering" to "🏗️",
        "software engineering" to "💻",
        "system design" to "🔧",
        "technical analysis" to "📊",
        "quality control" to "✅",
        "project management" to "📋",
        "structural integrity" to "🏗️",
        "engineering ethics" to "⚖️",
        // Finance & Banking
        "investment banking" to "🏦",
        "risk management" to "⚠️",
        "portfolio analysis" to "📊",
        "financial derivatives" to "📈",
        "asset valuation" to "💰",
        "capital markets" to "📊",
        "corporate finance" to "💼",
        "financial statement" to "📑",
        "market analysis" to "📊",
        "credit assessment" to "💳",
        // Cryptography & Security
        "encryption" to "🔐",
        "cryptographic key" to "🔑",
        "authentication" to "🔒",
        "digital signature" to "✍️",
        "cybersecurity" to "🛡️",
        "firewall" to "🧱",
        "malware" to "🦠",
        "data breach" to "⚠️",
        "vulnerability" to "🎯",
        "zero-day exploit" to "💥",
        // Biochemistry
        "enzyme" to "🧬",
        "protein synthesis" to "🔄",
        "cellular respiration" to "🫁",
        "metabolic pathway" to "⚡",
        "amino acid" to "🧪",
        "genetic code" to "🧬",
        "membrane transport" to "🔄",
        "molecular biology" to "🔬",
        "cell signaling" to "📡",
        "biochemical reaction" to "⚗️"
    )

    // Add this property to expose the emoji
    var currentEmoji by mutableStateOf<String?>(null)
        private set

    // Replace the existing scroll position properties with these
    data class ScrollInfo(
        val index: Int = 0,
        val offset: Int = 0
    )

    // Add near the top with other properties
    private var _categoryScrollPosition = 0
    var categoryScrollPosition: Int = 0
        private set

    // Add this function
    fun saveCategoryScrollPosition(position: Int) {
        _categoryScrollPosition = position
    }

    // Add near the top with other properties
    private val _scrollPositions = mutableMapOf<Difficulty, ScrollInfo>()
    var currentScrollPosition: ScrollInfo = ScrollInfo()
        private set

    // Update save function to include offset
    fun saveScrollPosition(difficulty: Difficulty, index: Int, offset: Int) {
        val scrollInfo = ScrollInfo(index, offset)
        _scrollPositions[difficulty] = scrollInfo
        currentScrollPosition = scrollInfo
    }

    // Add near other state properties
    private var _isReturningFromGame by mutableStateOf(false)
    var isReturningFromGame: Boolean
        get() = _isReturningFromGame
        private set(value) {
            _isReturningFromGame = value
        }

    // Update this function
    fun onExitGame() {
        _isReturningFromGame = true
    }

    // Update loadSections to reset the flag
    fun loadSections(difficulty: Difficulty) {
        currentDifficulty = difficulty
        val sections = repository.getSectionsForDifficulty(difficulty)
        repository.updateSections(sections)
        currentSection = null
        currentWord = null
        translatedWord = null
        _isReturningFromGame = false  // Reset the flag after loading sections
    }

    private fun fetchNextWord() {
        if (currentWordIndex >= words.size) {
            currentWordIndex = 0
            previousWords.clear() // Clear previous words when starting new round

            val sectionWords = currentSection?.words ?: emptyList()
            val availableWords = sectionWords.filterNot { word -> 
                word in usedWords || word in incorrectWords 
            }

            if (availableWords.isEmpty()) {
                isGameOver = true
                currentWord = null
                translatedWord = null
                return
            }

            words = availableWords.take(10).shuffled()
        }

        if (currentWordIndex < words.size) {
            val word = words[currentWordIndex]
            // Store current word and its result before moving to next
            currentWord?.let { english ->
                translatedWord?.let { turkish ->
                    val currentWord = Word(english, turkish, currentDifficulty ?: Difficulty.BEGINNER)
                    previousWords.add(currentWord)
                    // Store if the word was answered correctly
                    previousAttempts.add(WordAttempt(
                        word = currentWord,
                        wasCorrect = currentWord in correctWords
                    ))
                }
            }
            currentWord = word.english
            translatedWord = word.turkish
            // Get emoji for the word (case insensitive)
            currentEmoji = emojiMap[word.english.lowercase()] ?: ""
        } else {
            isGameOver = true
            currentWord = null
            translatedWord = null
        }
    }

    fun onSwipeLeft() {
        correctAnswers++
        totalAttempts++
        score += 10
        currentWord?.let { english ->
            translatedWord?.let { turkish ->
                val word = Word(english, turkish, currentDifficulty ?: Difficulty.BEGINNER)
                correctWords.add(word)
                usedWords.add(word)
                updateProgress()
            }
        }
        updateAccuracy()
        currentWordIndex++
        fetchNextWord()
    }

    fun onSwipeRight() {
        totalAttempts++
        currentWord?.let { english ->
            translatedWord?.let { turkish ->
                val word = Word(english, turkish, currentDifficulty ?: Difficulty.BEGINNER)
                incorrectWords.add(word)
                usedWords.add(word)
                updateProgress()
            }
        }
        updateAccuracy()
        currentWordIndex++
        fetchNextWord()
    }

    private fun updateProgress() {
        currentSection?.let { section ->
            val totalWords = section.words.size
            val correctWordsCount = correctWords.count { it in section.words }
            val progress = correctWordsCount.toFloat() / totalWords

            // Always save the latest progress
            repository.updateSectionProgress(section.id, progress)
            
            // Immediately reload sections to update UI
            currentDifficulty?.let { difficulty ->
                val updatedSections = repository.getSectionsForDifficulty(difficulty)
                repository.updateSections(updatedSections)
            }
        }
    }

    private fun updateAccuracy() {
        accuracy = if (totalAttempts > 0) {
            correctAnswers.toFloat() / totalAttempts
        } else {
            0f
        }
    }

    fun getCorrectWords(): List<Word> = correctWords.toList()
    fun getIncorrectWords(): List<Word> = incorrectWords.toList()

    fun startGame(section: Section) {
        currentSection = section
        words = section.words.shuffled()
        currentWordIndex = 0
        incorrectWords.clear()
        correctWords.clear()
        score = 0
        isGameOver = false
        accuracy = 0f
        correctAnswers = 0
        totalAttempts = 0
        
        // Reset emoji first
        currentEmoji = null
        
        // Set up first word directly instead of calling fetchNextWord()
        if (words.isNotEmpty()) {
            val word = words[0]  // Get first word
            currentWord = word.english
            translatedWord = word.turkish
            // Update emoji for the new word
            currentEmoji = emojiMap[word.english.lowercase()] ?: ""
        }
    }

    fun getDifficultyProgress(difficulty: Difficulty): Float {
        return repository.getDifficultyProgress(difficulty)
    }

    fun getSectionProgress(sectionId: String): Float {
        return repository.getSectionProgress(sectionId)
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            languageManager.setLanguage(languageCode)
        }
    }

    fun getString(key: String): String {
        return languageManager.getString(key)
    }

    // Update onRewind function
    fun onRewind() {
        // Add isLoading check to prevent spam
        if (previousWords.isNotEmpty() && 
            previousAttempts.isNotEmpty() && 
            currentWordIndex > 0 && 
            !isAnimatingBack) {
            
            isAnimatingBack = true
            slideDirection = 1 // Start sliding current card to right
            
            // Get the previous attempt and update scores
            val lastAttempt = previousAttempts.removeAt(previousAttempts.lastIndex)
            if (lastAttempt.wasCorrect) {
                correctAnswers--
                score -= 10
                correctWords.remove(lastAttempt.word)
            }
            totalAttempts--
            usedWords.remove(lastAttempt.word)
            
            // Update accuracy
            updateAccuracy()
            
            // Store the word we'll show next
            pendingWord = previousWords.removeAt(previousWords.lastIndex)
            
            // Update after animation finishes
            viewModelScope.launch {
                delay(250)
                currentWordIndex--
                currentWordIndex = currentWordIndex.coerceAtLeast(0)
                currentWord = pendingWord?.english
                translatedWord = pendingWord?.turkish
                currentEmoji = pendingWord?.english?.lowercase()?.let { emojiMap[it] } ?: ""
                slideDirection = -1 // Slide in from left
                
                delay(250)
                slideDirection = 0
                isAnimatingBack = false
                pendingWord = null
                
                // Update progress
                updateProgress()
            }
        }
    }
}
