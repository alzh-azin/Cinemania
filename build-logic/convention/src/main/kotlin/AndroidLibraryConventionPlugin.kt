import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import utils.configureKotlinAndroid
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import utils.getAndroidApplication
import utils.getAndroidLibrary
import utils.getKotlinAndroid
import utils.getKotlinSerialization

class AndroidLibraryConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getAndroidLibrary())
            plugin(target.getKotlinAndroid())
        }

        target.run {

            extensions.configure<LibraryExtension>{

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildFeatures {
                    buildConfig = true
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }



                configureKotlinAndroid(this)
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }

}