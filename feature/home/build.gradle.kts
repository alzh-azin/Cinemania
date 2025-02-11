plugins {
    alias(libs.plugins.cinemania.android.library.ui)
    alias(libs.plugins.cinemania.android.hilt)
}

android {
    namespace = "com.example.cinemania.feature.home"
}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designSystem)
    implementation(projects.feature.component)
}
