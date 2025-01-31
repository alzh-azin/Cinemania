import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.getDaggerHilt
import utils.getKapt
import utils.libs

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply {
            plugin(target.getKapt())
            plugin(target.getDaggerHilt())
        }

        target.run {
            dependencies {
                "implementation"(libs.findLibrary("androidx-hilt-navigation-compose").get())
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("androidx-hilt-common").get())
                "kapt"(libs.findLibrary("hilt.compiler").get())
                "kapt"(libs.findLibrary("hilt.ext.compiler").get())
            }
        }
    }
}