plugins {
    id(libs.plugins.custom.android.hilt.library.get().pluginId)
}

android {
    namespace = "app.migrosone.core.data"
}

dependencies {
    implementation(projects.contract)
    implementation(projects.navigation)
}