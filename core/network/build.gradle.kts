plugins {
    alias(libs.plugins.cinemania.android.library)
    alias(libs.plugins.cinemania.android.network)
    alias(libs.plugins.cinemania.android.hilt)
}

android {
    namespace = "com.example.cinemania.core.network"
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.domain)
}