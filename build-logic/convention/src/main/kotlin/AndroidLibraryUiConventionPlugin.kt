import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import utils.configureAndroidUi
import utils.getAndroidLibrary
import utils.getKotlinAndroid

class AndroidLibraryUiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        target.apply {
            plugin(target.getAndroidLibrary())
            plugin(target.getKotlinAndroid())
        }

        target.run {

            pluginManager.apply("cinemania.android.library")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidUi(extension)
        }
    }
}