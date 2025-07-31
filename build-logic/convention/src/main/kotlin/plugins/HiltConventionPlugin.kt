package plugins

import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                "implementation"(versionCatalog().findLibrary("hilt.android").get())
                "ksp"(versionCatalog().findLibrary("hilt-compiler").get())
                "implementation"(versionCatalog().findLibrary("hilt-navigation-compose").get())
//                add("implementation", versionCatalog().findLibrary("hilt-navigation-compose").get())
//                add("implementation", versionCatalog().findLibrary("hilt-android").get())
//                add("ksp", versionCatalog().findLibrary("hilt-compiler").get())
//                add("kspAndroidTest", versionCatalog().findLibrary("hilt-compiler").get())
//                add("kspTest", versionCatalog().findLibrary("hilt-compiler").get())
            }
        }
    }
}
