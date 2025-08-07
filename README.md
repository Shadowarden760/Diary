# 📔 Diary  

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg?logo=kotlin)](https://kotlinlang.org)  
[![License](https://img.shields.io/badge/license-MIT-black)](LICENSE)  

A minimalist Android app for **note-taking** and **weather tracking** in your region. Organize thoughts and stay prepared for the day ahead!  

---

## ✨ Features  
✅ **Notes Management**  
- Create, edit, and organize text-based notes.  
- Fast search and intuitive UI.  

🌤️ **Weather Integration**  
- Real-time weather forecasts for your location.  
- Minimalist weather cards with essential details.  

🎨 **Customization**  
- Dark/Light theme support.  
- Multi-language localization (English, Russian).  

---

## 🛠 Tech Stack  
- **Language**: [Kotlin](https://kotlinlang.org/)  
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Modern declarative UI)  
- **Networking**: [Ktor](https://ktor.io/) (Weather API integration)  
- **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/) (Local note storage)  
- **DI**: [Koin](https://insert-koin.io/) (Dependency injection)  
- **Async**: [Coroutines/Flow](https://kotlinlang.org/docs/coroutines-overview.html)  

---

## ⬇️ Installation  
### Option 1: Download APK  
Grab the latest release from [Releases](https://github.com/your-repo/releases).  

### Option 2: Build Locally  
```bash
git clone https://github.com/Shadowarden760/Diary.git
cd Diary
./gradlew assembleDebug
