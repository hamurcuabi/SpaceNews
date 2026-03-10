package app.migrosone.language

import androidx.compose.runtime.staticCompositionLocalOf

val LocalStringResourceManager = staticCompositionLocalOf<StringResourceManager> {
    error("No StringResourceManager provided")
}
