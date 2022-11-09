plugins {
    kotlin("plugin.serialization") version "1.7.21"
}

android {
    defaultConfig {
        applicationId = "com.doool.pokedex"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isShrinkResources = true
        }
    }
    namespace = "com.doool.pokedex"

    hilt { enableAggregatingTask = true }

    kapt {
        correctErrorTypes = true
    }
}

applyHiltDependencies()
applyRoomDependencies()
applyRetrofitDependencies()

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
}
