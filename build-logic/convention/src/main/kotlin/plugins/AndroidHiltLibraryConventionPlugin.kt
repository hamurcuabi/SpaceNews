package plugins

import AppConfig
import com.android.build.api.dsl.LibraryExtension
import convention.PluginConstants
import extensions.addImplementation
import extensions.addKsp
import extensions.addKspProject
import extensions.addProject
import extensions.configureKotlinAndroid
import extensions.implementKspViewModel
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            implementKspViewModel()
            project.pluginManager.apply {
                apply(plugin = PluginConstants.Plugins.AndroidLibrary)
                apply(plugin = PluginConstants.Plugins.KotlinAndroid)
                apply(plugin = PluginConstants.Plugins.HiltAndroid)
                apply(plugin = PluginConstants.Plugins.KotlinSerialization)
                apply(plugin = PluginConstants.Plugins.Ksp)
                apply(plugin = PluginConstants.Plugins.KotlinCompose)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    minSdk = AppConfig.MIN_SDK_VERSION

                    vectorDrawables {
                        useSupportLibrary = true
                    }

                    proguardFiles(
                        getDefaultProguardFile(PluginConstants.Defaults.ProguardAndroidRules),
                        PluginConstants.Defaults.ProguardRules
                    )
                }

                dependencies {
                    /*
                    addProject(PluginConstants.Projects.CORE_DI)
                    addProject(PluginConstants.Projects.CORE_DOMAIN)
                    addProject(PluginConstants.Projects.NETWORK_DATA)
                    addProject(PluginConstants.Projects.NETWORK_DOMAIN)
                    addProject(PluginConstants.Projects.CORE_ANNOTATIONS)
                    addImplementation(PluginConstants.Libraries.KotlinxCoroutinesCore)
                    addImplementation(PluginConstants.Libraries.KotlinSerializationJson)
                    addImplementation(PluginConstants.Libraries.KotlinxCoroutinesCore)

                    addImplementation(PluginConstants.Libraries.KoinCore)
                    addImplementation(PluginConstants.Libraries.KoinAnnotations)
                    addKsp(PluginConstants.Libraries.KoinKsp)
                    addKspProject(PluginConstants.Projects.PROCESSOR)
                     */

                    addImplementation(PluginConstants.Libraries.HiltAndroid)
                    addImplementation(PluginConstants.Libraries.AndroidxHiltNavigationCompose)
                    addKsp(PluginConstants.Libraries.HiltAndroidCompiler)
                    addKsp(PluginConstants.Libraries.AndroidxHiltCompiler)
                    addImplementation(PluginConstants.Libraries.AndroidxNavigation3Runtime)
                    addImplementation(PluginConstants.Libraries.AndroidxNavigation3Ui)
                }
            }
        }
    }
}
