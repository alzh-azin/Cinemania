plugins {
    alias(libs.plugins.cinemania.android.application.ui)
    alias(libs.plugins.cinemania.android.hilt)
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

    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.designSystem)
    implementation(projects.feature.home)
    implementation(projects.feature.details)
    implementation(projects.feature.search)
    implementation(projects.feature.favorites)
}