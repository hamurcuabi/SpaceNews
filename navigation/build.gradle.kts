plugins {
    id(libs.plugins.custom.kotlin.library.pure.get().pluginId)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
}