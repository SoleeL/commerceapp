package config

import org.gradle.api.JavaVersion
import java.io.File

object Config {

    private fun generateVersionName(): String {
        val major: Int = 1 // Actualizacion de la app
        val minor: Int = 0 // Actualizacion del negocio (modificaciones o nuevas funcionalidades)
        val patch: Int = 0 // Correcciones y

        // Obtener el número de commits desde el último tag
        val commitCount: Int = getGitCommitCount()

        return "$major.$minor.$patch-$commitCount"
    }

    private fun getGitCommitCount(): Int {
        val process = ProcessBuilder("git", "rev-list", "--count", "HEAD")
            .directory(File("./"))
            .start()
        val output = process.inputStream.bufferedReader().readText().trim()
        return output.toIntOrNull() ?: 0
    }

    private fun generateVersionCode(): Int {
        // Puedes usar el timestamp para asegurar que el versionCode siempre sea único
        val timestamp: Long = System.currentTimeMillis()
        return (timestamp / 1000).toInt() // Dividir por 1000 para evitar valores demasiado grandes
    }

    val android = AndroidConfig(
        minSdkVersion = 28,
        targetSdkVersion = 33,
        compileSdkVersion = 34,
        applicationId = "com.soleel.commerceapp",
        versionCode = generateVersionCode(),
        versionName = generateVersionName(),
        nameSpace = "com.soleel.commerceapp"
    )

    val jvm = JvmConfig(
        javaVersion = JavaVersion.VERSION_1_8,
        kotlinJvm = JavaVersion.VERSION_1_8.toString(),
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    )
}

data class AndroidConfig(
    val minSdkVersion : Int,
    val targetSdkVersion : Int,
    val compileSdkVersion : Int,
    val applicationId : String,
    val versionCode : Int,
    val versionName : String,
    val nameSpace: String
)

data class JvmConfig(
    val javaVersion : JavaVersion,
    val kotlinJvm : String,
    val freeCompilerArgs : List<String>
)