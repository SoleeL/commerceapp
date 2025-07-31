plugins {
    alias(libs.plugins.commerceapp.android.application)
    alias(libs.plugins.commerceapp.android.application.compose)
    alias(libs.plugins.commerceapp.android.hilt)
}

android {
    namespace = "com.soleel.commerceapp"
}

//dependencies {
//    implementation(projects.core.model)
//    implementation(projects.core.navigation)
//    implementation(projects.core.ui)
//
//    implementation(projects.feature.home)
//    implementation(projects.feature.salesprocess)
//}