plugins {
    alias(libs.plugins.cinemania.android.application.ui)
    alias(libs.plugins.cinemania.android.hilt)
    alias(libs.plugins.cinemania.android.serialization)
    alias(libs.plugins.cinemania.android.network)
}

android {
    namespace = "com.example.cinemania"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
}