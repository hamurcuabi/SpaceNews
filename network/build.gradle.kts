plugins {
    id(libs.plugins.custom.android.hilt.library.get().pluginId)
}

android {
    namespace = "app.migrosone.network"
}

dependencies {
    implementation(projects.contract)

    api(libs.bundles.network)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.noop)
    implementation(libs.hilt.android)
}