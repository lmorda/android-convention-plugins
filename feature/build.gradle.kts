plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.core.lifecycle)
}

android {
    namespace = "com.lmorda.feature"
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.compose.glide)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
}
