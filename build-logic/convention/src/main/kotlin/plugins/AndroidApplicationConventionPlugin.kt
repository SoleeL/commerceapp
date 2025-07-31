package plugins

import com.android.build.api.dsl.ApplicationExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlinx-serialization")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.apply {
                    targetSdk = Config.android.targetSdkVersion
                    applicationId = Config.android.applicationId
                    versionCode = Config.android.versionCode
                    versionName = Config.android.versionName

                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                configureAndroidKotlin(this)
                configureBuildTypes(this)
            }
        }
    }
}