package utils

import org.gradle.api.Project

internal fun Project.getAndroidApplication(): String {
    return libs.findPlugin("android-application").get().get().pluginId
}

internal fun Project.getComposeCompiler(): String {
    return libs.findPlugin("compose-compiler").get().get().pluginId
}

internal fun Project.getAndroidLibrary(): String {
    return libs.findPlugin("android-library").get().get().pluginId
}

internal fun Project.getKotlinAndroid(): String {
    return libs.findPlugin("jetbrains-kotlin-android").get().get().pluginId
}

internal fun Project.getKotlinSerialization(): String {
    return libs.findPlugin("kotlin-serialization").get().get().pluginId
}

internal fun Project.getKapt(): String {
    return libs.findPlugin("kapt").get().get().pluginId
}

internal fun Project.getKsp(): String {
    return libs.findPlugin("ksp").get().get().pluginId
}

internal fun Project.getDaggerHilt(): String {
    return libs.findPlugin("dagger-hilt-android").get().get().pluginId
}