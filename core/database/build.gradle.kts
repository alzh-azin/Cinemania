plugins {
    alias(libs.plugins.cinemania.android.library)
    alias(libs.plugins.cinemania.android.database)
    alias(libs.plugins.cinemania.android.hilt)
}

android {
    namespace = "com.example.cinemania.core.database"
}

dependencies {
    implementation(projects.core.domain)
}