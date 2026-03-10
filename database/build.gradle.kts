plugins {
    id(libs.plugins.custom.android.hilt.library.get().pluginId)
}

android {
    namespace = "app.migrosone.database"
}

dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}