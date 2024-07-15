plugins {
    alias(libs.plugins.convention.application)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.core.lifecycle)
}

android {
    namespace = "com.lmorda.convention"
    defaultConfig {
        applicationId = "com.lmorda.convention"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature"))
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}
