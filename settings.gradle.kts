pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Cinemania"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:data")
include(":core:domain")
include(":core:network")
include(":core:common")
include(":core:database")
include(":core:designSystem")
include(":feature:component")
include(":feature:details")
include(":feature:search")
include(":feature:home")
include(":core:ui")
include(":feature:favorites")
