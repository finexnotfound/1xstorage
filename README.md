# 1x Storage

> **Minimalist Liquid Glass Cloud Storage — Engineered by finex**  
> Free **50 GB Cloud Storage** for everyone with instant uploads, previewing, cloud link sharing, and Google Authentication.

[![Android](https://img.shields.io/badge/Platform-Android-000000.svg?style=flat&logo=android&logoColor=white)](#)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-000000.svg?style=flat&logo=kotlin&logoColor=white)](#)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-000000.svg?style=flat&logo=jetpackcompose&logoColor=white)](#)
[![Firebase](https://img.shields.io/badge/Cloud-Firebase%20Auth%20%26%20Firestore-000000.svg?style=flat&logo=firebase&logoColor=white)](#)
[![Storage](https://img.shields.io/badge/Storage-50%20GB%20Free-000000.svg?style=flat)](#)

---

## 📱 Direct APK Download

The ready-to-install debug APK is located in the root directory:

- 📥 **[Download 1x Storage APK](./1x_Storage_by_finex.apk)** (`1x_Storage_by_finex.apk`)

---

## 🌟 Key Features

### 1. Free 50 GB Cloud Storage
- Every account automatically receives **50.0 GB of free cloud storage space**.
- Live monochromatic liquid glass storage meter with real-time used/available quota tracking.
- Protected by finex end-to-end cloud encryption protocols.

### 2. Complete Authentication Suite
- **Email & Password Authentication**: Full sign-up and sign-in backed by Firebase Auth.
- **Continue with Google**: Built using the modern Android Credential Manager API and Firebase Google Authentication.
- **Quick Access Mode**: Instant 1-tap finex demo account for immediate zero-friction testing.

### 3. File Upload & Categorization
- Multi-format file picker for documents, PDFs, zips, code, and spreadsheets.
- Android Photo Picker for high-resolution images and videos.
- Automatic smart categorizations:
  - 🖼️ **Images**
  - 📄 **Documents**
  - 🎬 **Media**
  - 📦 **Archives**
  - 📁 **Other**
- Instant search filter and category tab switching.

### 4. Interactive File Preview
- Tap any file in **My Files** to launch the **Liquid Glass Preview Modal**.
- High-fidelity image rendering via **Coil**.
- Comprehensive file inspection: file size, MIME type, upload timestamp, and cloud synchronization status.
- Direct device launch via Android system intents.

### 5. Instant Cloud Sharing & Web Browser Viewer
- One-tap link generation with unique cloud share URLs.
- Triggers the native Android system share sheet.
- **Built-in Cloud Browser Viewer**: Replicates what recipients experience when opening the shared link in a web browser on the cloud, featuring cloud download actions, URL copy, and external browser launch.

---

## 🎨 Design Language: Monochromatic Liquid Glass

- **Strict Black & White Palette**: Pure Pitch Black (`#000000`), deep OLED noir surfaces, pure white highlights (`#FFFFFF`), and Apple system grays (`#8E8E93`, `#2C2C2E`). No distracting colors.
- **Smooth iOS Font**: Bundled **Inter** font family (`res/font/inter.ttf`) styled with bold and italic iOS typography scales.
- **Translucent Liquid Glass**: Frosted translucent cards with specular white top rims, soft inner sheens, continuous squircle corners, and subtle monochromatic liquid light refractions.

---

## 🛠️ Architecture & Tech Stack

| Layer | Technologies |
|---|---|
| **Language** | Kotlin 2.2.10 |
| **UI Framework** | Jetpack Compose + Material Design 3 |
| **Architecture** | MVVM (Model-View-ViewModel) + StateFlow |
| **Typography** | Local Google Font `Inter` (`res/font/inter.ttf`) |
| **Image Loading** | Coil Compose |
| **Identity & Auth** | Firebase Authentication + Android Credential Manager |
| **Persistence** | Firebase Firestore + Local Upload Cache |
| **Build System** | Gradle (Kotlin DSL) with Version Catalogs |

---

## 🚀 Building & Running

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17 or JDK 21
- Android SDK 36 (Minimum SDK 24)

### Gradle Tasks
```bash
# Compile debug APK
gradle assembleDebug

# Run unit and Robolectric tests
gradle :app:testDebugUnitTest
```

---

*Crafted by **finex**.*
