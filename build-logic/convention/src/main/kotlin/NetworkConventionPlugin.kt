import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.getKsp
import utils.libs

class NetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getKsp())
        }

        target.run {
            dependencies {
                "implementation"(libs.findLibrary("retrofit").get())
                "implementation"(libs.findLibrary("okhttp").get())
                "implementation"(libs.findLibrary("convertor-moshi").get())
                "implementation"(libs.findLibrary("moshi-kotlin").get())
                "implementation"(libs.findLibrary("logging-interceptor").get())
                "ksp"(libs.findLibrary("moshi-kotlin-codegen").get())
                "implementation"(libs.findLibrary("paging-runtime").get())
            }
        }
    }
}