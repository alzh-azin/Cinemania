package utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureSourceSets(){

    extensions.configure<KotlinMultiplatformExtension>{
        sourceSets{
            val commonMain = getByName("commonMain")
            val androidMain = getByName("androidMain")
            val commonTest = getByName("commonTest")

            androidMain.dependencies {
                implementation(libs.findLibrary("androidx-activity-compose").get())
            }
            commonMain.dependencies {
                implementation(libs.findLibrary("compose-runtime").get())
                implementation(libs.findLibrary("compose-foundation").get())
                implementation(libs.findLibrary("compose-material3").get())
                implementation(libs.findLibrary("compose-ui").get())
                implementation(libs.findLibrary("compose-components-resources").get())
                implementation(libs.findLibrary("compose-components-uiToolingPreview").get())

                implementation(libs.findLibrary("androidx-lifecycle-viewmodelCompose").get())
                implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())

                implementation(libs.findLibrary("koin-core").get())
                implementation(libs.findLibrary("koin-compose").get())
                implementation(libs.findLibrary("koin-compose-viewmodel").get())
            }
            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin-test").get())
            }
        }
    }
}