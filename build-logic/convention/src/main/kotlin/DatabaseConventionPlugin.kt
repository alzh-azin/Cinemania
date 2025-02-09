import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.getKsp
import utils.libs

class DatabaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getKsp())
        }

        target.run {
            dependencies {
                "implementation"(libs.findLibrary("room-runtime").get())
                "implementation"(libs.findLibrary("room-ktx").get())
                "ksp"(libs.findLibrary("room-compiler").get())
            }
        }
    }
}