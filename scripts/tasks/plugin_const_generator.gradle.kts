tasks.register("generateLibsObject") {
    doLast {
        val libsFile = file("${project.rootDir}/gradle/libs.versions.toml")
        val outputDir = file("${project.rootDir}/build-logic/convention/src/main/kotlin/convention")
        val outputFile = File(outputDir, "PluginConstants.kt")
        val projects = parseProjects()
        val config = parseTOML(libsFile = libsFile, projects = projects)
        outputDir.mkdirs()
        outputFile.writeText(generateKotlinFileContent(config, "PluginConstants"))
    }
}

data class Config(
    val defaults: String,
    val versions: Map<String, String>,
    val libraries: Map<String, String>,
    val plugins: Map<String, String>
)

fun parseTOML(
    libsFile: File,
    projects: String,
    defaults: Map<String, String> = mapOf(
        "Android" to "org.jetbrains.kotlin.android",
        "Library" to "com.android.library",
        "ComposePlugin" to "org.jetbrains.kotlin.plugin.compose",
        "TestInstrumentationRunner" to "androidx.test.runner.AndroidJUnitRunner",
        "ConsumerRules" to "consumer-rules.pro",
        "JavaLibrary" to "java-library",
        "MavenPublish" to "maven-publish",
        "ProguardRules" to "proguard-rules.pro",
        "ProguardAndroidRules" to "proguard-android.txt"

    )
): Config {
    val libraries = mutableMapOf<String, String>()
    val plugins = mutableMapOf<String, String>()
    val versions = mutableMapOf<String, String>()

    val defaultModuleConstants = buildString {
        appendLine("")
        appendLine("    object Defaults {")
        defaults.forEach { (key, value) ->
            appendLine("        const val $key = \"$value\"")
        }
        appendLine("    }")

        append(projects)
    }

    var insideLibrariesSection = false
    var insidePluginsSection = false
    var insideVersionsSection = false

    libsFile.forEachLine { line ->
        when {
            line.startsWith("[versions]") -> {
                insideLibrariesSection = false
                insidePluginsSection = false
                insideVersionsSection = true
            }

            line.startsWith("[libraries]") -> {
                insideLibrariesSection = true
                insidePluginsSection = false
                insideVersionsSection = false
            }

            line.startsWith("[plugins]") -> {
                insideLibrariesSection = false
                insidePluginsSection = true
                insideVersionsSection = false
            }

            line.contains("=") -> {
                val parts = line.split(" = ")
                val key = parts[0].trim()
                val value = parts[0].trim().replace("-", ".").lowercase()

                val camelCaseKey =
                    key.split("-")
                        .joinToString("") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
                        .replaceFirstChar { it.uppercase() }

                when {
                    insideLibrariesSection -> {
                        libraries[camelCaseKey] = value
                    }

                    insidePluginsSection -> {
                        val regex = """id\s*=\s*"([^"]+)"""".toRegex()
                        val matchResult = regex.find(line)
                        val idValue = matchResult?.groups?.get(1)?.value ?: ""
                        plugins[camelCaseKey] = idValue
                    }

                    insideVersionsSection -> {
                        versions[camelCaseKey] = parts[1].trim().removeSurrounding("\"")
                    }
                }
            }
        }
    }

    return Config(defaultModuleConstants, versions, libraries, plugins)
}

fun parseProjects(): String {
    val exclude = "app"
    val settingsFile = file("${project.rootDir}/settings.gradle.kts")

    val modules = settingsFile.readLines()
        .filter { it.contains("include(\":") }
        .map { it.substringAfter("include(\":").substringBefore("\")") }
        .filter { it != exclude }

    val kotlinObjectContent = buildString {
        appendLine("")
        appendLine("    object Projects {")
        modules.forEach { module ->
            val constantName = module.replace(':', '_').replace("-", "_").uppercase()
            appendLine("        const val $constantName = \":$module\"")
        }
        appendLine("    }")
    }
    return kotlinObjectContent
}

fun generateKotlinFileContent(config: Config, name: String): String {
    return buildString {
        appendLine("package convention\n")
        appendLine("object $name {")
        append(config.defaults)
        appendLine("")
        appendLine("    object Versions {")
        config.versions.forEach { (key, value) ->
            appendLine("        const val ${key} = \"$value\"")
        }
        appendLine("    }")
        appendLine("")
        appendLine("    object Libraries {")
        config.libraries.forEach { (key, value) ->
            appendLine("        const val $key = \"$value\"")
        }
        appendLine("    }")
        appendLine("")
        appendLine("    object Plugins {")
        config.plugins.forEach { (key, value) ->
            appendLine("        const val $key = \"$value\"")
        }
        appendLine("    }")
        appendLine("}")
    }
}