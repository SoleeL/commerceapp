import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.soleel.commerceapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        // Application module
        register("androidApplication") {
            id = "commerceapp.android.application"
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "commerceapp.android.application.compose"
            implementationClass = "plugins.ComposeApplicationConventionPlugin"
        }

        // Library module

        register("androidLibrary") {
            id = "commerceapp.android.library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "commerceapp.android.library.compose"
            implementationClass = "plugins.ComposeLibraryConventionPlugin"
        }

        // Library dependency

        register("androidHilt") {
            id = "commerceapp.android.hilt"
            implementationClass = "plugins.HiltConventionPlugin"
        }

        register("androidRoom") {
            id = "commerceapp.android.room"
            implementationClass = "plugins.RoomConventionPlugin"
        }
    }
}