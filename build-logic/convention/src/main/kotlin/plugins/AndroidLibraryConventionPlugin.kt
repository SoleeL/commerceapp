package plugins

import com.android.build.gradle.LibraryExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("kotlin-android")
                apply("kotlinx-serialization")
                apply("kotlin-parcelize")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidKotlin(this)
                configureBuildTypes(this)
                defaultConfig.apply {
                    targetSdk = Config.android.targetSdkVersion
                }
            }
        }
    }
}