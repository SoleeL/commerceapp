package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project


fun Project.configureBuildTypes(
    extension: CommonExtension<*, *, *, *>
) {
    extension.apply {

        buildFeatures {
            buildConfig = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                isJniDebuggable = false
                buildConfigField("Boolean", "DEMO", "false")
            }

            getByName("debug") {
                isMinifyEnabled = false
                isJniDebuggable = true

                if (extension is ApplicationExtension) {
                    extension.buildTypes.getByName("debug").apply {
                        applicationIdSuffix = ".debug"
                        resValue("string", "app_name", "commerce_app_debug")
                    }
                }

                buildConfigField("Boolean", "DEMO", "false")
            }

            maybeCreate("demo").apply {
                initWith(getByName("debug"))

                if (extension is ApplicationExtension) {
                    extension.buildTypes.getByName("demo").apply {
                        applicationIdSuffix = ".demo"
                        resValue("string", "app_name", "commerce_app_demo")
                    }
                }

                buildConfigField("Boolean", "DEMO", "true")
            }
        }
    }
}