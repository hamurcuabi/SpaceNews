enum class PaparaDimensions(val dimension: String) {
    API(dimension = "api");

    companion object {

        fun getDimensions() = PaparaDimensions
            .values()
            .map { it.dimension }
    }
}

enum class PaparaBuildTypes(val type: String) {
    DEBUG(type = "debug"),
    RELEASE(type = "release")
}

enum class PaparaProductFlavors(
    val variant: String,
    val dimension: String,
    val applicationIdSuffix: String
) {
    DEV_API(
        variant = "development",
        dimension = PaparaDimensions.API.dimension,
        applicationIdSuffix = ".dev"
    ),
    PROD_API(
        variant = "production",
        dimension = PaparaDimensions.API.dimension,
        applicationIdSuffix = ""
    );
}
