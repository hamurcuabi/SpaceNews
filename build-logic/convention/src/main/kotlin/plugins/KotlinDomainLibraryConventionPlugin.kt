package plugins

import convention.PluginConstants
import extensions.addImplementation
import extensions.addKsp
import extensions.addKspProject
import extensions.addProject
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinDomainLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply(plugin = PluginConstants.Defaults.JavaLibrary)
                apply(plugin = PluginConstants.Plugins.JetbrainsKotlinJvm)
                apply(plugin = PluginConstants.Plugins.KotlinSerialization)
                apply(plugin = PluginConstants.Plugins.Ksp)
            }

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            tasks.withType<JavaCompile>().configureEach {
                sourceCompatibility = JavaVersion.VERSION_17.toString()
                targetCompatibility = JavaVersion.VERSION_17.toString()
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }

            dependencies {
                /*
                addProject(PluginConstants.Projects.CORE_DOMAIN)
                addProject(PluginConstants.Projects.CORE_ANNOTATIONS)
                addImplementation(PluginConstants.Libraries.KotlinxCoroutinesCore)

                addImplementation(PluginConstants.Libraries.KoinCore)
                addImplementation(PluginConstants.Libraries.KoinAnnotations)
                addKsp(PluginConstants.Libraries.KoinKsp)
                addKspProject(PluginConstants.Projects.PROCESSOR)

                 */
            }
        }
    }
}
