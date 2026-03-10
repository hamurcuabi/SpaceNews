plugins {
    id(libs.plugins.custom.kotlin.library.pure.get().pluginId)
}

dependencies {
    implementation(projects.navigation)
    implementation(libs.androidx.navigation3.ui)
}
