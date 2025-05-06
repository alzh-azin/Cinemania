plugins {
    alias(libs.plugins.cinemania.android.library.ui)
    alias(libs.plugins.cinemania.android.hilt)
}
android {
    namespace = "com.example.cinemania.feature.favorites"
}
dependencies{

    implementation(projects.core.ui)
}