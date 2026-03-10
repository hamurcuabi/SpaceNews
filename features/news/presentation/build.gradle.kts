plugins {
    id(libs.plugins.custom.android.library.compose.get().pluginId)
}

android {
    namespace = "app.migrosone.feature.news.presentation"
}

dependencies {

    implementation(projects.features.news.contract)
    implementation(projects.features.news.domain)

    implementation(projects.core.presentation)
    implementation(projects.navigation)
    implementation(projects.uikit)

    testImplementation(libs.bundles.test.all)
}
