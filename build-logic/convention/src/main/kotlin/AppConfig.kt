@file:Suppress("MissingPackageDeclaration")


object AppConfig {

    const val APPLICATION_ID = "app.migrosone"
    const val MIN_SDK_VERSION = 27
    const val COMPILE_SDK_VERSION = 36
    const val TARGET_SDK_VERSION = 36

    const val VERSION_CODE = 1

    private const val VERSION_MAJOR = 1
    private const val VERSION_MINOR = 0
    private const val VERSION_PATCH = 0

    val versionName
        get() = "$VERSION_MAJOR.$VERSION_MINOR.$VERSION_PATCH"
}
