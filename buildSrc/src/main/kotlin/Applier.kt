import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.applyAndroidConfig() {
    extensions.getByType<BaseExtension>().run {
        compileSdkVersion(33)

        defaultConfig {
            minSdk = 23
            targetSdk = 33

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFile("consumer-rules.pro")
        }

        buildTypes {
            getByName("release") {
                minifyEnabled(true)
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "11"
                languageVersion = "1.8"
            }
        }
    }
}

fun Project.applyComposeConfig() {
    extensions.getByType<BaseExtension>().run {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
        }
    }
}

private val Project.libs get() = the<LibrariesForLibs>()

private fun DependencyHandlerScope.implementation(vararg list: Any) {
    list.forEach {
        add("implementation", it)
    }
}

private fun DependencyHandlerScope.kapt(vararg list: Any) {
    list.forEach {
        add("kapt", it)
    }
}

private fun DependencyHandlerScope.androidTestImplementation(vararg list: Any) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun Project.applyComposeDependencies() {
    applyComposeConfig()
    project.dependencies {
        implementation(libs.bundles.compose)
        androidTestImplementation(libs.compose.ui.test)
    }
}

fun Project.applyHiltDependencies() {
    project.dependencies {
        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)
    }
}

fun Project.applyRoomDependencies() {
    project.dependencies {
        implementation(libs.bundles.room)
        kapt(libs.room.compiler)
    }
}

fun Project.applyRetrofitDependencies() {
    project.dependencies {
        implementation(libs.bundles.retrofit)
    }
}

fun Project.applyLifecycleDependencies() {
    project.dependencies {
        implementation(libs.bundles.lifecycle)
    }
}

fun Project.applyCoil() {
    project.dependencies {
        implementation(libs.coil)
    }
}

fun Project.applyPlugin() {
    val hasBuildGradle = buildFile.isFile
    when {
        name == "pokedex" -> {
            apply {
                plugin("com.android.application")
                plugin("kotlin-android")
                plugin("kotlin-kapt")
                plugin("dagger.hilt.android.plugin")
            }
            applyAndroidConfig()
        }

        !hasBuildGradle -> {}
        else -> {
            apply {
                plugin("com.android.library")
                plugin("kotlin-android")
                plugin("kotlin-kapt")

                when (this@applyPlugin.path) {
                    ":presentation", ":download:feature", ":widget" -> {
                        apply {
                            plugin("dagger.hilt.android.plugin")
                        }
                    }
                }
            }
            applyAndroidConfig()
        }
    }
}
