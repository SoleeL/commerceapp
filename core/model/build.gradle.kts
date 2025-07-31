plugins {
    alias(libs.plugins.commerceapp.android.library)
}

android {
    namespace = "com.soleel.commerceapp.core.model"
}

dependencies {
    implementation(projects.core.ui)
}