plugins {
    id(libs.plugins.custom.android.application.compose.get().pluginId)
}

android {
    namespace = "app.migrosone"

    buildTypes {
        getByName(PaparaBuildTypes.DEBUG.type) {
            signingConfig = signingConfigs.getByName(PaparaBuildTypes.DEBUG.type)

            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
        }
    }

    flavorDimensions += PaparaDimensions.getDimensions()

    val flavorConfigs = mapOf(
        PaparaProductFlavors.DEV_API to mapOf(
            "baseUrl" to "https://api.spaceflightnewsapi.net/v4/",
            "appName" to "Test - MigrosOne"
        ),

        PaparaProductFlavors.PROD_API to mapOf(
            "baseUrl" to "https://api.spaceflightnewsapi.net/v4/",
            "appName" to "MigrosOne"
        )
    )

    productFlavors {
        PaparaProductFlavors.values().forEach {
            create(it.variant) {
                dimension = it.dimension
            }
        }

        flavorConfigs.forEach { (flavor, config) ->
            getByName(flavor.variant) {
                dimension = flavor.dimension
                applicationIdSuffix = flavor.applicationIdSuffix

                buildConfigField("String", "BASE_URL", "\"${config["baseUrl"]}\"")

                resValue("string", "app_name", config["appName"] as String)
            }
        }
    }
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
