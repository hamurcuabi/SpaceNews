plugins {
    id(libs.plugins.custom.android.application.compose.get().pluginId)
}

android {
    namespace = "app.migrosone"
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.presentation)

    implementation(projects.contract)
    implementation(projects.database)
    implementation(projects.language)
    implementation(projects.navigation)
    implementation(projects.network)
    implementation(projects.uikit)
    implementation(libs.timber)

    implementation(projects.features.news.contract)
    implementation(projects.features.news.data)
    implementation(projects.features.news.domain)
    implementation(projects.features.news.presentation)
}
