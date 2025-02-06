plugins {
    alias(libs.plugins.cinemania.android.library)
    alias(libs.plugins.cinemania.android.hilt)
    alias(libs.plugins.cinemania.android.network)
}

android {
    namespace = "com.example.cinemania.core.data"
}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.core.common)
}