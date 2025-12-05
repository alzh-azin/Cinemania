package utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureDebugDependencies() {
    dependencies {
        add("debugImplementation", libs.findLibrary("compose-uiTooling").get())
    }
}