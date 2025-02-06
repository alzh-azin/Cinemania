plugins {
    alias(libs.plugins.cinemania.android.library.ui)
    alias(libs.plugins.cinemania.android.hilt)
}

android {
    namespace = "com.example.cinemania.feature.component"
}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.designSystem)
}