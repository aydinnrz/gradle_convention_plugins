package extensions

import com.android.build.api.dsl.CommonExtension
import config.Config
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidKotlin(
    extension: CommonExtension<*, *, *, *>,
) {
    with(extension) {
        namespace = Config.android.nameSpace
        compileSdk = Config.android.compileSdkVersion
        defaultConfig.apply {
            minSdk = Config.android.minSdkVersion
            testInstrumentationRunner = "androidx.test.runner.AndroidJunitRunner"
            vectorDrawables.useSupportLibrary = true
        }
        compileOptions {
            sourceCompatibility = Config.jvm.javaVersion
            targetCompatibility = Config.jvm.javaVersion
        }
        packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

        dependencies {
            add("implementation", versionCatalog().findLibrary("androidx-ktx").get())
            add("implementation", versionCatalog().findLibrary("androidx-lifecycle-runtime-ktx").get())
            add("testImplementation", versionCatalog().findLibrary("junit").get())
            add("androidTestImplementation", versionCatalog().findLibrary("androidx-junit").get())
            add("androidTestImplementation", versionCatalog().findLibrary("androidx-espresso-core").get())
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Config.jvm.kotlinJvm
            freeCompilerArgs = freeCompilerArgs + Config.jvm.freeCompilerArgs
        }
    }
}