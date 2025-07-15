<h1 align="center">📚 FocusVerse</h1>
<p align="center">
A modern Android app for focused, structured learning — built with Jetpack Compose and Firebase.
</p>

---

## 🧩 Overview

**FocusVerse** is a productivity-first Android app designed to help learners organize their educational content — including YouTube videos, notes, and PDFs — into meaningful, structured sections.

Whether you’re prepping for exams or learning independently, FocusVerse supports your flow with intuitive UI and real-time cloud sync.

---

## 🚀 Features

- 📁 Create and manage **Sections** by topic or subject
- 🎥 Add and track **YouTube videos**
- 📝 Write, edit, and delete **notes**
- 📄 Upload and open **PDFs**
- 📅 View **Today's Focus** — your daily priority list
- ✅ Completion tracking for all items
- 🔐 Secure **Firebase Authentication**
- 🔄 Real-time sync via **Cloud Firestore**
- 🌈 Beautiful Jetpack Compose UI
- 🧲 Drag-and-drop (coming soon)

---

## 🛠️ Tech Stack

| Layer            | Technology                           |
|------------------|---------------------------------------|
| UI               | Jetpack Compose, Material 3           |
| Architecture     | MVVM + Clean Architecture             |
| State Management | ViewModel + StateFlow + MutableState |
| Backend          | Firebase Auth, Firestore              |
| APIs             | YouTube Data API, ZenQuotes API       |
| Storage (WIP)    | DataStore, Room DB (planned)          |

---

## 🗂️ Folder Structure

<pre><code>## 🗂️ Folder Structure FocusVerse/ ├── ui/ │ ├── components/ → Reusable UI components │ └── screens/ → Composable screens (Home, Section, AddItem) │ ├── viewModel/ → ViewModels (HomeViewModel, SectionViewModel, etc.) │ ├── repository/ │ ├── impl/ → FirebaseRepositoryImpl, etc. │ └── interfaces/ → Interfaces for abstraction │ ├── model/ → Data models (Video, Note, Pdf, FocusReference) │ ├── state/ → UI state classes and screen states │ ├── api/ → YouTube API, ZenQuotes API integration │ ├── navigation/ → Navigation graph and routes │ ├── uifiles/ → Constants, config, local `const.kt` │ ├── utils/ → Helper functions, formatters, extensions </code></pre>

- `FocusVerse/`
  - `ui/`
    - `components/` — Reusable UI components
    - `screens/` — Composable screens like Home, Section, etc.
  - `viewModel/` — Holds all ViewModels
  - `repository/`
    - `impl/` — Repository implementations (e.g., FirebaseRepositoryImpl)
    - `interfaces/` — Abstract interfaces
  - `model/` — Data classes like Video, Note, Pdf, FocusReference
  - `state/` — UI state classes
  - `api/` — YouTube API, ZenQuotes API integration
  - `navigation/` — Navigation graph and screen routes
  - `uifiles/` — Constants and config (e.g., `const.kt`)
  - `utils/` — Helpers and utility functions


---

## 🔐 Security

This repo follows GitHub best practices:

- `.gitignore` excludes:
  - `google-services.json`
  - `local.properties`
  - `const.kt` (contains API keys)
  - Build files: `/build/`, `.gradle/`, `.idea/`
- API keys are stored in `local.properties` and accessed via `BuildConfig`

---

## 📸 Screenshots

> *(UI screenshots to be added here soon)*


