plugins {
    alias(libs.plugins.cinemania.android.library)
    alias(libs.plugins.cinemania.android.hilt)
    alias(libs.plugins.cinemania.android.network)
}

android {
    namespace = "com.example.cinemania.core.domain"
}

dependencies {
    implementation(projects.core.network)
}