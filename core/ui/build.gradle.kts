plugins {
    alias(libs.plugins.cinemania.android.library.ui)
    alias(libs.plugins.cinemania.android.serialization)
}

android {
    namespace = "com.example.cinemania.core.ui"
}

dependencies{
    implementation(projects.core.designSystem)
}
