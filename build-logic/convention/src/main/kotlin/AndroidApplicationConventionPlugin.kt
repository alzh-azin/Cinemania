
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.kotlin.dsl.configure
import utils.configureKotlinAndroid
import utils.getAndroidApplication
import utils.getKotlinAndroid
import utils.libs

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply{
            plugin(target.getAndroidApplication())
            plugin(target.getKotlinAndroid())
        }

        target.run {

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()


                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
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
        }
    }

}