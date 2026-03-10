# Technical Stack & Project Architecture

This document provides an overview of the technical stack and modular architecture of the **SpaceNews** project.

## 🛠 Technical Stack

### Core
- **Kotlin**
- **Jetpack Compose**: Modern UI toolkit
- **Hilt**: Dependency injection
- **Coroutines & Flow**: Asynchronous programming and reactive data streams
- **Navigation3**: Bleeding-edge declarative navigation system

### Data Layer
- **Retrofit**: Type-safe HTTP client for API interactions
- **OkHttp**: Underlying HTTP client with interceptors
- **Room**: Local persistence with SQLite
- **Gson**: JSON serialization/deserialization
- **Coil**: Image loading for Compose

### UI & UX
- **Custom UIKit**: Centralized Design System with support for multi-theming
- **Lottie**: Vector animations
- **Chucker**: In-app HTTP inspector
- **LeakCanary**: Memory leak detection

### Testing
- **JUnit 5 / Jupiter**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Turbine**: Small testing library for Kotlin Flows
- **Coroutines Test**: Testing utilities for coroutines

---

## 🏗 Project Structure

The project follows a **highly modular architecture** based on **Clean Architecture** principles and **SoC (Separation of Concerns)**.

### 📦 Modular Hierarchy

#### 1. Features (`:features:*`)
Each feature is isolated and further subdivided into Clean Architecture layers:
- **`:features:news:contract`**: Defines the public API, navigation destinations, and arguments.
- **`:features:news:domain`**: Contains business logic, Use Cases, domain models, and repository interfaces.
- **`:features:news:data`**: Implements repositories, API services, local entities, and mappers.
- **`:features:news:presentation`**: UI implementation using Compose and ViewModels with **Sealed UI States**.

#### 2. Core Modules (`:core:*`)
Shared logic used across multiple features or layers:
- **`:core:data`**: Generic data layer helpers and common result states.
- **`:core:presentation`**: Shared UI utilities (e.g., `IntentUtils` for browser/sharing).

#### 3. Infrastructure Modules
- **`:app`**: The "orchestrator" module. Handles Hilt DI composition and global navigation graph.
- **`:contract`**: Global common contracts and high-level interfaces.
- **`:database`**: Centralized Room database configuration and shared DAOs.
- **`:network`**: Global Retrofit/OkHttp configuration and network monitoring.
- **`:navigation`**: Shared navigation infrastructure and `NavGraphProvider` interface.
- **`:uikit`**: The project's Design System (Themes, Colors, Typography, and Reusable Components like `KtScaffold`, `KtToolbar`).
- **`:language`**: Localized string resource management (`StringResourceManager`) to avoid hardcoded strings.

#### 4. Build Logic (`:build-logic`)
The project utilizes **Gradle Convention Plugins** located in the `build-logic` directory to centralize and standardize build configurations.

---

## 🛠 Gradle Convention Plugins

Instead of repeating boilerplate configuration in every `build.gradle.kts` file, the project uses custom convention plugins to enforce consistency across modules.

### Key Plugins
- **`gradlePlugins.android.application.compose`**: Configures the main Android application with Compose, Hilt, and common Android settings.
- **`gradlePlugins.android.library.compose`**: Applies to feature `presentation` modules. Sets up Compose, Hilt, Navigation3, and common UI dependencies.
- **`gradlePlugins.android.library.data`**: Used by `data` modules. Configures Android Library settings, Room, Hilt, and Network dependencies.
- **`gradlePlugins.kotlin.library.domain`**: Configures pure Kotlin modules for business logic (domain), including Hilt and Coroutines.
- **`gradlePlugins.kotlin.library.contract`**: Small plugin for feature contract modules, focusing on serialization and navigation commands.
- **`gradlePlugins.android.library.hilt`**: A utility plugin for modules that only need Hilt dependency injection without extra UI or Data overhead.

### Benefits
- **Single Source of Truth**: Update a dependency version or a compiler flag in one place (`build-logic`) and it reflects everywhere.
- **Clean Build Files**: Individual module `build.gradle.kts` files are extremely small, containing only the specific dependencies for that module.
- **Encapsulation**: Implementation details of the build system (like Proguard rules or SDK versions) are hidden from feature developers.

---

## 🎨 Architectural Patterns

### UI State Management
The project uses **Sealed UI States** to represent different screen conditions (`Loading`, `Success`, `Error`, `Empty`). This ensures:
- **Type Safety**: The UI can only access data relevant to its current state.
- **Predictability**: Clear transitions between different states.
- **Readability**: Composable screens are split into state-specific modular functions.

### Repository Pattern
The `data` layer abstracts data sources (Network vs. Disk) using repositories. It leverages **Flows** to provide a reactive stream of data from the local database (SSoT - Single Source of Truth) to the presentation layer.

### String Resource Manager
Localized strings are managed through a custom `StringResourceManager` injected via DI. This allows the data and domain layers to indirectly reference localized strings without being tied to the Android `Context`.
