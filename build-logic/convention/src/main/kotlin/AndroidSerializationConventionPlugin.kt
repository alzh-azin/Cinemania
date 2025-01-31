import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.getKotlinSerialization
import utils.libs

class AndroidSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getKotlinSerialization())
        }

       target.run {

           dependencies {
               "implementation"(libs.findLibrary("kotlinx-serialization-json").get())
           }
       }
    }
}