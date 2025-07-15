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

FocusVerse/
├── ui/ → Composables
├── viewModel/ → ViewModels
├── repository/ → Data sources (Firebase, local)
├── model/ → Data models (Video, Note, etc.)
├── state/ → UI state holders
├── api/ → YouTube & Quotes
├── navigation/ → Navigation graph
├── uifiles/ → Constants (e.g. API keys)
├── utils/ → Helpers


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


