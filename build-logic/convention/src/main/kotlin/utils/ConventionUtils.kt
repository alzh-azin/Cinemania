package utils

import org.gradle.api.Project

internal fun Project.getAndroidApplication(): String {
    return libs.findPlugin("androidApplication").get().get().pluginId
}

internal fun Project.getComposeCompiler(): String {
    return libs.findPlugin("composeCompiler").get().get().pluginId
}

internal fun Project.getAndroidLibrary(): String {
    return libs.findPlugin("androidLibrary").get().get().pluginId
}

internal fun Project.getKotlinMultiplatform(): String {
    return libs.findPlugin("kotlinMultiplatform").get().get().pluginId
}

internal fun Project.getComposeMultiplatform(): String {
    return libs.findPlugin("composeMultiplatform").get().get().pluginId
}
