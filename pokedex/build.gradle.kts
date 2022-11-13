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

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))
}
