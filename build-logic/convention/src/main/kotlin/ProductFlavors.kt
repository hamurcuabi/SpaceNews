enum class AppDimensions(val dimension: String) {
    API(dimension = "api");

    companion object {

        fun getDimensions() = values()
            .map { it.dimension }
    }
}

enum class AppBuildTypes(val type: String) {
    DEBUG(type = "debug"),
    RELEASE(type = "release")
}

enum class AppProductFlavors(
    val variant: String,
    val dimension: String,
    val applicationIdSuffix: String,
    val baseUrl: String,
    val appName: String
) {
    DEV_API(
        variant = "development",
        dimension = AppDimensions.API.dimension,
        applicationIdSuffix = ".dev",
        "https://api.spaceflightnewsapi.net/v4/",
        "Test - MigrosOne"
    ),
    PROD_API(
        variant = "production",
        dimension = AppDimensions.API.dimension,
        applicationIdSuffix = "",
        "https://api.spaceflightnewsapi.net/v4/",
        "MigrosOne"
    );
}
