plugins {
    alias(libs.plugins.cinemania.android.library.ui)
}

android {
    namespace = "com.example.cinemania.feature.component"
}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designSystem)
}