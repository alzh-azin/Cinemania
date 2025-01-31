import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import utils.configureAndroidUi
import utils.getComposeCompiler
import utils.getKsp

class AndroidApplicationUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getKsp())
            plugin(target.getComposeCompiler())
        }

        target.run {
            pluginManager.apply("cinemania.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidUi(extension)
        }
    }
}