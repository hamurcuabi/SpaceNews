import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {

        register("gitHooksPlugin") {
            id = "gradlePlugins.gitHooksPlugin"
            implementationClass = "plugins.GitHooksPlugin"
        }

        register("androidApplicationConventionPlugin") {
            id = "gradlePlugins.android.application.compose"
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }

        register("androidComposeLibraryConventionPlugin") {
            id = "gradlePlugins.android.library.compose"
            implementationClass = "plugins.AndroidComposeLibraryConventionPlugin"
        }

        register("kotlinLibraryConventionPlugin") {
            id = "gradlePlugins.kotlin.library.pure"
            implementationClass = "plugins.KotlinLibraryConventionPlugin"
        }

        register("androidDataLibraryConventionPlugin") {
            id = "gradlePlugins.android.library.data"
            implementationClass = "plugins.AndroidDataLibraryConventionPlugin"
        }

        register("kotlinDomainLibraryConventionPlugin") {
            id = "gradlePlugins.kotlin.library.domain"
            implementationClass = "plugins.KotlinDomainLibraryConventionPlugin"
        }

        register("kotlinContractLibraryConventionPlugin") {
            id = "gradlePlugins.kotlin.library.contract"
            implementationClass = "plugins.KotlinContractLibraryConventionPlugin"
        }
        register("androidHiltLibraryConventionPlugin") {
            id = "gradlePlugins.android.library.hilt"
            implementationClass = "plugins.AndroidHiltLibraryConventionPlugin"
        }
    }
}
