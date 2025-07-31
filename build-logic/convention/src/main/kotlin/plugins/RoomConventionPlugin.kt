package plugins

import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", versionCatalog().findLibrary("room-runtime").get())
                add("implementation", versionCatalog().findLibrary("room-ktx").get())
                add("ksp", versionCatalog().findLibrary("room-compiler").get())
            }
        }
    }
}
