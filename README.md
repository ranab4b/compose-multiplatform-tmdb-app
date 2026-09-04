# 🎬 Compose Multiplatform TMDB Demo

A modern, production-ready cross-platform application for **Android & iOS** built with **Compose Multiplatform (CMP)**, **Clean Architecture**, **SOLID Principles**, **Koin**, **Ktor**, and explicit **DTO-to-Domain Mapping**.

---

## 🌟 Key Features

* **100% Shared UI:** Declarative Compose UI running natively across Android and iOS.
* **DTO-to-Domain Separation:** Network models are strictly isolated from Domain and UI entities.
* **Infinite Scroll Pagination:** Automated page fetching as the user reaches the end of the movie list.
* **Debounced Search:** Real-time movie search against TMDB Search API with a 500ms debounce.
* **Dynamic Genre Mapping:** Client-side conversion of TMDB genre IDs into human-readable genre labels.
* **Dependency Injection:** Fully decoupled architecture wired together using **Koin**.

---
## 🌟 Demo

[![Compose Multiplatform Demo Android/iOS](https://img.youtube.com/vi/FEIMejeYA4c/maxresdefault.jpg)](https://youtube.com/shorts/FEIMejeYA4c)

> 🎬 **[Click here to watch the full YouTube Shorts demo](https://youtube.com/shorts/FEIMejeYA4c)**

---

## 🏛️ Architecture & Project Structure

The project follows strict **Clean Architecture** principles, splitting concerns into three distinct layers:

```text
                  +-----------------------------------+
                  |        Presentation Layer         |
                  |  (Compose UI, ViewModel, State)   |
                  +-----------------+-----------------+
                                    |
                                    v
                  +-----------------+-----------------+
                  |           Domain Layer            |
                  | (Entities, Use Cases, Contracts)  |
                  +-----------------+-----------------+
                                    ^
                                    |
                  +-----------------+-----------------+
                  |            Data Layer             |
                  | (DTOs, Mappers, Ktor DataSource)  |
                  +-----------------+-----------------+
```

### Directory Structure

```text
composeApp/src/commonMain/kotlin/org/example/tmdb/
├── core/
│   └── constants/          # Centralized API endpoints & constants
├── di/                     # Dependency injection modules (Koin)
└── features/movies/
    ├── data/
    │   ├── datasources/    # Remote network calls via Ktor
    │   ├── mappers/        # DTO to Domain transformation
    │   ├── models/         # Network DTOs (Data Transfer Objects)
    │   └── repositories/   # Repository implementation
    ├── domain/
    │   ├── entities/       # Pure Kotlin business entities
    │   ├── repositories/   # Abstract repository contracts
    │   └── usecases/       # Single-responsibility business logic
    └── presentation/
        ├── screens/        # Material 3 Compose UI screens
        └── viewmodel/      # UI State management & ViewModel
```

---

## 💡 Architectural Highlights

### Why DTOs (Data Transfer Objects)?
Network schemas (`MovieDto`, `GenreDto`) are completely separated from business models (`Movie`).
1. **API Decoupling:** API JSON structural changes only require updating the Data Layer mapper, leaving Domain and UI code intact.
2. **Null Safety & Sanitization:** DTOs handle missing or nullable network fields, while Domain entities receive clean, fallback-ready non-null properties.
3. **UI Model Protection:** The UI layer works exclusively with clean Kotlin domain models without network annotations (`@SerialName`) leaking into UI components.

### SOLID Principles Applied
* **Single Responsibility Principle (SRP):** `MovieRemoteDataSource` handles HTTP operations, `MovieMapper` transforms models, and `MovieViewModel` manages presentation state.
* **Open/Closed Principle (OCP):** Domain logic relies on `MovieRepository` interfaces. New data sources (e.g., local database caching) can be added without modifying existing Use Cases.
* **Liskov Substitution Principle (LSP):** `MovieRepositoryImpl` safely substitutes `MovieRepository` everywhere without breaking repository contracts.
* **Dependency Inversion Principle (DIP):** Higher-level modules do not depend on lower-level modules; both depend on abstractions (`MovieRepository`) wired at runtime using **Koin**.

---

## 🛠️ Tech Stack & Libraries

| Library | Purpose |
| :--- | :--- |
| **Compose Multiplatform** | Cross-platform declarative UI framework |
| **Koin** | Dependency Injection (`koin-core`, `koin-compose`) |
| **Ktor Client** | Asynchronous HTTP Networking (`ktor-client-content-negotiation`) |
| **KotlinX Serialization** | JSON Parsing & Serialization |
| **Coil 3** | Multiplatform Image Loading & Caching |
| **Coroutines & Flow** | Asynchronous Programming & Reactive State Handling |

---

## 🚀 Getting Started

### Prerequisites
* **Android Studio** (Ladybug or newer) with the **Kotlin Multiplatform** plugin installed.
* **Xcode** (for running iOS target).
* Java Development Kit (JDK) 17 or higher.

### Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/ComposeMultiPlatformTMDBDemo.git
   cd ComposeMultiPlatformTMDBDemo
   ```

2. **Configure API Key:**
   Open `composeApp/src/commonMain/kotlin/org/example/tmdb/core/constants/ApiConstants.kt` and insert your TMDB API key:
   ```kotlin
   object ApiConstants {
       const val API_KEY = "YOUR_TMDB_API_KEY_HERE"
   }
   ```

3. **Run the App:**
   * **Android:** Select `composeApp` run configuration in Android Studio and hit **Run**.
   * **iOS:** Open `iosApp/iosApp.xcworkspace` in Xcode, or execute directly via Android Studio's iOS target runner.

4. **Run Unit Tests:**
   ```bash
   ./gradlew check
   ```
