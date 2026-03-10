pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "MigrosOneCase"
include(":app")
include(":contract")
include(":core")
include(":core:data")
include(":core:presentation")
include(":database")
include(":features")
include(":features:news")
include(":features:news:contract")
include(":features:news:data")
include(":features:news:domain")
include(":features:news:presentation")
include(":language")
include(":navigation")
include(":network")
include(":uikit")