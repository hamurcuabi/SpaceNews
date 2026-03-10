package plugins

import AppConfig
import com.android.build.api.dsl.LibraryExtension
import convention.PluginConstants
import extensions.addImplementation
import extensions.addKsp
import extensions.addPlatformImplementation
import extensions.addProject
import extensions.configureKotlinAndroid
import extensions.implementKspViewModel
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class AndroidComposeLibraryConventionPlugin : Plugin<Project> {

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
                configureAndroidCompose(this)

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
            }

            dependencies {
                addProject(PluginConstants.Projects.CONTRACT)
                addProject(PluginConstants.Projects.LANGUAGE)
                addProject(PluginConstants.Projects.NAVIGATION)

                addImplementation(PluginConstants.Libraries.HiltAndroid)
                addImplementation(PluginConstants.Libraries.AndroidxHiltNavigationCompose)
                addKsp(PluginConstants.Libraries.HiltAndroidCompiler)
                addKsp(PluginConstants.Libraries.AndroidxHiltCompiler)

                addImplementation(PluginConstants.Libraries.AndroidxNavigation3Runtime)
                addImplementation(PluginConstants.Libraries.AndroidxConstraintlayoutCompose)
                addImplementation(PluginConstants.Libraries.CoilCompose)
                addImplementation(PluginConstants.Libraries.AndroidxNavigation3Ui)
                addImplementation(PluginConstants.Libraries.AndroidxUiTooling)
                addImplementation(PluginConstants.Libraries.AndroidxMaterial3)
                addImplementation(PluginConstants.Libraries.AndroidxUiToolingPreview)
                addImplementation(PluginConstants.Libraries.UiGraphics)
                addImplementation(PluginConstants.Libraries.AndroidxUi)
                addImplementation(PluginConstants.Libraries.AndroidxCoreKtx)
                addImplementation(PluginConstants.Libraries.AndroidxLifecycleRuntimeKtx)
                addImplementation(PluginConstants.Libraries.AndroidxActivityCompose)
                addPlatformImplementation(PluginConstants.Libraries.AndroidxComposeBom)
            }
        }
    }
}
