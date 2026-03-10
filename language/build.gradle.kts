plugins {
    id(libs.plugins.custom.android.hilt.library.get().pluginId)
}

android {
    namespace = "app.migrosone.language"
}

dependencies{
    implementation(libs.androidx.hilt.navigation.compose)
}
