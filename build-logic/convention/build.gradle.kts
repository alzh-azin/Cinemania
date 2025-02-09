import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.cinemania.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {

    plugins {
        plugins {
            register("androidApplication") {
                id = "cinemania.android.application"
                implementationClass = "AndroidApplicationConventionPlugin"
            }
        }

        register("androidApplicationUi") {
            id = "cinemania.android.application.ui"
            implementationClass = "AndroidApplicationUiConventionPlugin"
        }

        register("androidLibrary") {
            id = "cinemania.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryUi"){
            id = "cinemania.android.library.ui"
            implementationClass = "AndroidLibraryUiConventionPlugin"
        }

        register("androidSerialization"){
            id = "cinemania.android.serialization"
            implementationClass = "AndroidSerializationConventionPlugin"
        }

        register("hilt"){
            id = "cinemania.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }

        register("network"){
            id = "cinemania.android.network"
            implementationClass = "NetworkConventionPlugin"
        }

        register("database"){
            id = "cinemania.android.database"
            implementationClass = "DatabaseConventionPlugin"
        }
    }

}