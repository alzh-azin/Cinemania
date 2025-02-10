plugins {
    alias(libs.plugins.cinemania.android.library)
    alias(libs.plugins.cinemania.android.hilt)
}

android {
    namespace = "com.example.cinemania.core.domain"
}

dependencies {
    implementation(projects.core.common)
}
