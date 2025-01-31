pluginManagement{
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google()
        mavenCentral()
    }

}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")