plugins {
    id(libs.plugins.custom.android.library.compose.get().pluginId)
}

android {
    namespace = "app.migrosone.feature.news.domain"
}

dependencies {

    implementation(projects.contract)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.bundles.test.all)

}
