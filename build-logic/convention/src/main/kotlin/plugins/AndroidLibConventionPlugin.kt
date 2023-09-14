package plugins

import com.android.build.api.dsl.LibraryExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project){
            with(pluginManager) {
                apply("com.android.library")
                apply("kotlin-android")
            }
            extensions.configure<LibraryExtension> {
                configureAndroidKotlin(this)
                defaultConfig.apply {
                    targetSdk = Config.android.targetSdkVersion
                }
                configureBuildTypes(this)
            }
        }
    }
}