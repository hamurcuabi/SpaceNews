package extensions

import AppConfig
import com.android.build.api.dsl.CommonExtension
import convention.PluginConstants
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

private const val JDK_VERSION = 17

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureKotlin() {
    extensions.configure<KotlinAndroidProjectExtension> {
        this.compilerOptions {
            jvmToolchain(JDK_VERSION)
        }
    }
}

internal fun Project.addPlatformImplementation(
    library: String,
    versionCatalog: VersionCatalog = libs
) {
    dependencies {
        add("implementation", platform(versionCatalog.findLibrary(library).get()))
    }
}

internal fun Project.addTestPlatformImplementation(
    library: String,
    versionCatalog: VersionCatalog = libs
) {
    dependencies {
        add("testImplementation", platform(versionCatalog.findLibrary(library).get()))
    }
}

internal fun Project.addImplementation(library: String, versionCatalog: VersionCatalog = libs) {
    dependencies {
        add("implementation", versionCatalog.findLibrary(library).get())
    }
}

internal fun Project.addProject(projectName: String) {
    dependencies {
        add("implementation", project(projectName))
    }
}

internal fun Project.addKspProject(projectName: String) {
    dependencies {
        add("ksp", project(projectName))
    }
}

internal fun Project.addKapt(library: String, versionCatalog: VersionCatalog = libs) {
    dependencies {
        add("kapt", versionCatalog.findLibrary(library).get())
    }
}

internal fun Project.addKsp(library: String, versionCatalog: VersionCatalog = libs) {
    dependencies {
        add("ksp", versionCatalog.findLibrary(library).get())
    }
}

internal fun Project.addDebugImplementation(
    library: String,
    versionCatalog: VersionCatalog = libs
) {
    dependencies {
        add("debugImplementation", versionCatalog.findLibrary(library).get())
    }
}

internal fun Project.addTestImplementation(library: String, versionCatalog: VersionCatalog = libs) {
    dependencies {
        add("testImplementation", versionCatalog.findLibrary(library).get())
    }
}

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = AppConfig.COMPILE_SDK_VERSION

        defaultConfig {
            minSdk = AppConfig.MIN_SDK_VERSION
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        configureKotlin()
    }
}

internal fun Project.implementKspViewModel() {
    plugins.withId(PluginConstants.Plugins.Ksp) {
        extensions.configure<com.google.devtools.ksp.gradle.KspExtension> {
            arg("USE_COMPOSE_VIEWMODEL", "true")
        }
    }
}

internal fun Project.getProjectProperty(propertyName: String): String =
    project.findProperty(propertyName)?.toString() ?: generatePropertyError(propertyName)

internal fun generatePropertyError(property: String): Nothing = error("Couldn't find $property")
