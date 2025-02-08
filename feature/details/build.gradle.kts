plugins {
    alias(libs.plugins.cinemania.android.library.ui)
    alias(libs.plugins.cinemania.android.hilt)
    alias(libs.plugins.cinemania.android.serialization)
}

android {
    namespace = "com.example.cinemania.feature.details"
}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.designSystem)
}
