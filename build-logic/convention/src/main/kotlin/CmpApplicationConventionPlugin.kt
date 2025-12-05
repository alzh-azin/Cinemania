import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.configureAndroid
import utils.configureDebugDependencies
import utils.configureSourceSets
import utils.configureKmpTargets
import utils.getAndroidApplication
import utils.getComposeCompiler
import utils.getComposeMultiplatform
import utils.getKotlinMultiplatform


class CmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        pluginManager.apply(getAndroidApplication())
        pluginManager.apply(getKotlinMultiplatform())
        pluginManager.apply(getComposeCompiler())
        pluginManager.apply(getComposeMultiplatform())

        configureKmpTargets()
        configureSourceSets()
        configureAndroid()
        configureDebugDependencies()
    }
}