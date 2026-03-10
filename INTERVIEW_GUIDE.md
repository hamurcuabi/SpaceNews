# Technical Interview Guide: Android & Clean Architecture

This guide contains curated technical questions and comprehensive answers specifically tailored to the **MigrosOne** architecture and modern Android standards.

---

## 🏗 Part 1: Architecture & Multi-Module

### Q1: Why does each feature have a separate `:contract` module?
**Answer:** The `:contract` module serves as the public interface of a feature. It contains only what other modules need to know: global navigation destinations and the data classes required for arguments.
- **Benefit:** It prevents circular dependencies and significantly reduces build times. If only the implementation (`:presentation` or `:data`) changes, other modules that depend on the `:contract` don't need to be recompiled.

### Q2: Explain the "Dependency Inversion" principle between the `:domain` and `:data` modules.
**Answer:** In Clean Architecture, the `:domain` module defines **Repository Interfaces**, and the `:data` module implements them. 
- **Mechanism:** The `:domain` module doesn't know about Retrofit or Room. It only knows about the business logic. Hilt then "injects" the implementation from `:data` into the Use Case in `:domain` at runtime.

### Q3: What are Gradle Convention Plugins and why are they used in this project?
**Answer:** They are custom plugins (located in `build-logic`) that centralize shared build logic.
- **Why:** Instead of repeating Hilt, Compose, and SDK configurations in 15+ modules, we apply `gradlePlugins.android.library.compose` once. This ensures that every module is configured identically, reducing "Gradle sprawl" and making updates (like Kotlin versions) 10x faster.

### Q4: Why is the `:domain` module a pure Kotlin module (non-android)?
**Answer:** The Domain layer should contain pure business logic, free from Android frameworks. 
- **Benefit:** This makes it easier to test (unit tests run instantly on JVM without an emulator) and ensures the business logic isn't coupled to a specific platform (facilitates potential future migrations like KMP).

---

## 🎨 Part 2: Jetpack Compose & UI State

### Q5: How do Sealed UI States (Loading, Success, Error) improve UI reliability?
**Answer:** By using a `sealed interface` for the `UiState`, we ensure the UI can only ever be in one "valid" state at a time.
- **Exhaustiveness:** In the Composable, we use a `when` block. Kotlin forces us to handle all cases (`Loading`, `Success`, `Error`). This eliminates "zombie states" where a loading spinner is visible while data is also showing.

### Q6: Why do we use `collectAsStateWithLifecycle()` instead of `collectAsState()`?
**Answer:** `collectAsStateWithLifecycle` is lifecycle-aware. It automatically stops collecting when the app goes into the background (and restarts when it comes back). 
- **Direct Benefit:** It prevents unnecessary resource consumption (CPU/Memory) and potential crashes when trying to update the UI while the activity is inactive.

### Q7: Explain the custom "UIKit" approach used in the project.
**Answer:** The project isolates common UI components (Scaffolds, Toolbars, Buttons) in a `:uikit` module. 
- **Consistency:** It uses `CompositionLocal` to provide centralized themes (Colors, Typography). This ensures that a change to the "Primary Color" in `:uikit` reflects across all feature modules instantly.

---

## 💉 Part 3: Hilt & Dependency Injection

### Q8: How do you provide a dependency that needs a `Context` (like SharedPreferences) in a multi-module architecture without leaking the Context to the Domain layer?
**Answer:** We define an interface in the `:domain` (or a shared `:contract`) and implement it in a module that has access to the Context (like `:data` or `:language`). 
- **Example:** `StringResourceManager` implementation is in `:language` and uses `@ApplicationContext`. The ViewModel only receives the interface, never the Context itself.

### Q9: What is the purpose of `@Qualifier` annotations (like `@BaseUrl`)?
**Answer:** Qualifiers are used when there are multiple bindings for the same type. 
- **Context:** If the project needs to connect to two different APIs, we create two `@Qualifier` annotations so Hilt knows which `String` (URL) to inject into which Retrofit instance.

---

## 🌊 Part 4: Kotlin, Coroutines & Flow

### Q10: Explain the use of `flatMapLatest` in the `NewsListViewModel`.
**Answer:** It is used mainly for the search functionality. When a user types "A", a network call starts. If they type "AB" immediately after, `flatMapLatest` cancels the "A" request and starts the "AB" request.
- **Result:** It ensures the UI only ever displays the results for the *latest* query, avoiding race conditions.

### Q11: What is the benefit of `stateIn(SharingStarted.WhileSubscribed(5000L))`?
**Answer:** It converts a cold `Flow` into a hot `StateFlow`.
- **Caching:** It caches the latest value for the UI.
- **Optimization:** The `5000L` (5 seconds) delay keeps the flow alive during configuration changes (like screen rotation), so the data isn't re-fetched unnecessarily, but shuts it down if the user actually leaves the screen.

### Q12: Why is `debounce` critical for search implementations?
**Answer:** It pauses the execution of the flow until the user stops typing for a specific duration (e.g., 300ms). This prevents triggering a network request for every single keystroke, saving bandwidth and backend resources.

---

## 🌐 Part 5: Networking & Security

### Q13: How would you implement SSL Pinning in this project?
**Answer:** In the `OkHttpClient.Builder` (located in `:network`), I would use `CertificatePinner.Builder()`. I'd add the hostname and the SHA-256 hashes of the server's certificates.
- **Security:** This ensures the app only communicates with the trusted server and prevents Man-in-the-Middle (MitM) attacks.

### Q14: What is an Interceptor in OkHttp and how is it used in MigrosOne?
**Answer:** An Interceptor is a middleware that can modify requests or responses. 
- **Example:** We use `ChuckerInterceptor` for debugging. We could also add an `ApiKeyInterceptor` to automatically inject authentication headers into every outgoing request.

---

## 💾 Part 6: Persistence & SSoT

### Q15: What is the "Single Source of Truth" (SSoT) pattern?
**Answer:** Instead of the UI observing the API directly, the API saves data to the **Local Database (Room)**. The UI only observes the Database.
- **Benefit:** The app works offline, data updates are reflected across all screens (e.g., toggling a favorite), and the UI is always in sync with the persistent layer.

### Q16: How do you handle database migrations in Room?
**Answer:** Use the `Migration` class to define SQL statements for schema changes and increment the version number. In modern Room, `AutoMigration` can handle simple field additions automatically.

---

## 🔎 Part 7: Google Clean Architecture (Community Favorites)

### Q17: What is the difference between a "Domain Model" and a "Data Entity"?
**Answer:** 
- **Data Entity:** A class annotated with `@Entity` for Room or reflecting the JSON structure (DTO). It's focused on storage/network details.
- **Domain Model:** A clean class in the `:domain` layer for business logic. 
- **Why:** Mapping between them prevents database changes from breaking the UI.

### Q18: Should Use Cases contain state?
**Answer:** No. Use Cases should be **stateless**. They should take input, perform an operation (like fetching or calculating), and return an output (Flow or Result). If state is needed, it belongs in the ViewModel.

### Q19: Why do we avoid using `Result<T>` directly from the Domain layer to the UI in some cases?
**Answer:** While `Result` is fine, Google often recommends mapping to a `UiState` in the ViewModel. This prevents the UI from having to interpret "Error Codes" and allows it to just render what the ViewModel tells it to.

### Q20: How does "Interface Segregation" apply to Repositories?
**Answer:** A feature shouldn't be forced to depend on a massive repository with 50 methods. We split repositories by feature (e.g., `NewsRepository`, `UserRepository`) so that modules only link to the data they actually need.

---

## 🚀 Part 8: Bonus Performance Question

### Q21: How does R8/ProGuard work and why does it matter?
**Answer:** R8 performs code shrinking (removing unused code), obfuscation (renaming classes), and optimization. 
- **Impact:** It reduces the APK size significantly and makes it harder for malicious actors to reverse-engineer the application.
