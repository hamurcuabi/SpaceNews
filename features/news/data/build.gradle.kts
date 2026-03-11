plugins {
    id(libs.plugins.custom.android.library.data.get().pluginId)
}

android {
    namespace = "app.migrosone.feature.news.data"
}

dependencies {
    implementation(projects.features.news.domain)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.timber)
    testImplementation(libs.bundles.test.all)
}
