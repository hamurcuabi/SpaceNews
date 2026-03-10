plugins {
    id(libs.plugins.custom.android.library.compose.get().pluginId)
}

android {
    namespace = "app.migrosone.core.presentation"
}

dependencies {

    implementation(projects.contract)
    implementation(projects.navigation)
    implementation(projects.uikit)
    implementation(projects.language)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.kotlin.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)

}