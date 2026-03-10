package plugins

import AppConfig
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import convention.PluginConstants
import extensions.addDebugImplementation
import extensions.addImplementation
import extensions.addKsp
import extensions.addPlatformImplementation
import extensions.configureKotlinAndroid
import extensions.implementKspViewModel
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            implementKspViewModel()
            project.pluginManager.apply {
                apply(plugin = PluginConstants.Plugins.AndroidApplication)
                apply(plugin = PluginConstants.Plugins.KotlinAndroid)
                apply(plugin = PluginConstants.Plugins.KotlinCompose)
                apply(plugin = PluginConstants.Plugins.HiltAndroid)
                apply(plugin = PluginConstants.Plugins.Ksp)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                defaultConfig {
                    applicationId = AppConfig.APPLICATION_ID
                    minSdk = AppConfig.MIN_SDK_VERSION
                    targetSdk = AppConfig.TARGET_SDK_VERSION
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.versionName
                    versionCode = VersionCodeUtils.buildVersionCode

                    vectorDrawables {
                        useSupportLibrary = true
                    }

                    proguardFiles(
                        getDefaultProguardFile(PluginConstants.Defaults.ProguardAndroidRules),
                        PluginConstants.Defaults.ProguardRules
                    )
                }
            }
        }
    }
}

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    project.pluginManager.apply {
        apply(plugin = PluginConstants.Plugins.KotlinCompose)
    }

    commonExtension.apply {
        buildFeatures {
            compose = true
            buildConfig = true
        }
    }

    dependencies {

        addImplementation(PluginConstants.Libraries.HiltAndroid)
        addImplementation(PluginConstants.Libraries.AndroidxHiltNavigationCompose)
        addKsp(PluginConstants.Libraries.HiltAndroidCompiler)
        addKsp(PluginConstants.Libraries.AndroidxHiltCompiler)

        addImplementation(PluginConstants.Libraries.AndroidxNavigation3Runtime)
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
