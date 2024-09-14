pluginManagement {
    repositories {

        maven {
            url = uri("https://inexus.samentic.com/repository/samentic-android")
            credentials {
                username = "azin.alizadeh"
                password = "K}bWjB%k7PpAJ>."
            }
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
            url = uri("https://inexus.samentic.com/repository/samentic-android")
            credentials {
                username = "azin.alizadeh"
                password = "K}bWjB%k7PpAJ>."
            }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Cinemania"
include(":app")
 