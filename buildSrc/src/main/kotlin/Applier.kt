import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
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
            }
        }
    }
}

fun Project.applyComposeConfig() {
    extensions.getByType<BaseExtension>().run {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = compose_compiler_version
        }
    }
}

private fun DependencyHandlerScope.implementation(vararg list: String) {
    list.forEach {
        add("implementation", it)
    }
}

private fun DependencyHandlerScope.kapt(vararg list: String) {
    list.forEach {
        add("kapt", it)
    }
}

private fun DependencyHandlerScope.androidTestImplementation(vararg list: String) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun Project.applyComposeDependencies() {
    applyComposeConfig()

    project.dependencies {
        implementation(
            "androidx.compose.ui:ui:$compose_version",
            "androidx.compose.animation:animation:$compose_version",
            "androidx.compose.material:material:$compose_version",
            "androidx.compose.ui:ui-tooling:$compose_version",
            "androidx.compose.runtime:runtime:$compose_version",
            "androidx.compose.runtime:runtime-livedata:$compose_version"
        )

        androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    }
}

fun Project.applyHiltDependencies() {
    project.dependencies {
        implementation("com.google.dagger:hilt-android:$hilt_version")
        kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    }
}

fun Project.applyRoomDependencies() {
    project.dependencies {
        implementation("androidx.room:room-ktx:$room_version")
        implementation("androidx.room:room-runtime:$room_version")
        kapt("androidx.room:room-compiler:$room_version")
    }
}

fun Project.applyRetrofitDependencies() {
    project.dependencies {
        implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
        implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

        add("implementation", platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
        implementation(
            "com.squareup.okhttp3:okhttp",
            "com.squareup.okhttp3:logging-interceptor"
        )
    }
}

fun Project.applyLifecycleDependencies() {
    project.dependencies {
        implementation(
            "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1",
            "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1",
            "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha06"
        )
    }
}

fun Project.applyCoil() {
    project.dependencies {
        implementation(
            "io.coil-kt:coil-compose:$coil_version"
        )
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
                plugin("com.github.ben-manes.versions")
            }
            applyAndroidConfig()
        }
        !hasBuildGradle -> {}
        else -> {
            apply {
                plugin("com.android.library")
                plugin("kotlin-android")
                plugin("kotlin-kapt")
                plugin("com.github.ben-manes.versions")

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
