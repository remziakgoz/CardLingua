# CardLingua - Language Learning Flashcard App

![Platform](https://img.shields.io/badge/platform-Android-blue?logo=android)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-%E2%9C%A8-blue)
![Material 3](https://img.shields.io/badge/Material%203-%E2%9C%A8-blue)
![License](https://img.shields.io/badge/license-MIT-green)

CardLingua is a modern Android application designed to help users learn English through an interactive flashcard system. Built with Jetpack Compose and following Material 3 design principles, it offers an engaging way to expand vocabulary and improve language skills.

---

## 📸 Screenshots
Add screenshots here for a more visual README!

  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/f57b9ff7-6a99-4bd6-9371-c503a15a470d" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/04b295d0-2e6a-49f9-a404-4f131b988187" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/19868df0-21ee-4e42-a20e-e4b6f660b993" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/3a66cfb2-4e65-44b1-9488-c37b49b64e4d" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/7036e8c8-94a0-496c-a5ef-fbf98e3e1700" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/c34d2de4-1956-4f4a-925b-c21d6abffdc8" width="220"/>
  <img src="https://github.com/remziakgoz/CardLingua/assets/111980306/52a19707-8ff6-4f3b-9c03-81e3fc299870" width="220"/>

---

## ✨ Features

### Core Functionality
- **Flashcard Learning System**: Interactive card-based learning with swipe gestures
- **Progress Tracking**: Visual progress indicators for each category and difficulty level
- **Multi-Language Support**: Available in English and Turkish
- **Difficulty Levels**: Four progressive difficulty tiers
  - Beginner: Basic Words
  - Intermediate: Common Phrases
  - Advanced: Complex Words
  - Expert: Advanced Terms
- **Onboarding**: Friendly onboarding experience for new users
- **Game Modes**: Play, review, and track your progress

### Technical Features
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Persistent Storage**: Progress and settings saved using DataStore
- **State Management**: ViewModel architecture with Kotlin Flows
- **Dependency Injection**: Hilt for dependency management
- **Clean Architecture**: Organized in domain, data, and presentation layers

---

## 🏗️ Project Structure

```text
CardLingua/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/remziakgoz/cardlingua/
│   │       │   ├── data/         # Data sources, repositories, managers
│   │       │   ├── domain/       # Core models
│   │       │   ├── di/           # Dependency injection (Hilt modules)
│   │       │   ├── presentation/ # UI screens, components, viewmodels
│   │       │   ├── ui/           # Theme and UI utilities
│   │       │   ├── CardLinguaApplication.kt
│   │       │   └── MainActivity.kt
│   │       ├── res/              # Resources (drawables, values, etc.)
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Giraffe (or newer)
- Android SDK 24+
- Kotlin 1.8+

### Setup & Build
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/CardLingua.git
   cd CardLingua
   ```
2. **Open in Android Studio**
3. **Build & Run:**
  - Click **Run** ▶️ or use `Shift+F10`.

> **Note:** No API keys or special configuration required. All data is local.

---

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to open an issue or submit a pull request.

---

## 📄 License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 👤 Author
- [Remzi Akgoz](https://github.com/remziakgoz)
