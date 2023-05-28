plugins {
    kotlin("plugin.serialization") version "1.8.21"
}

android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
                argument("room.incremental", "true")
                argument("room.expandProjection", "true")
            }
        }
    }
    namespace = "com.doool.pokedex.data"
}

applyRoomDependencies()
applyHiltDependencies()
applyRetrofitDependencies()

dependencies {

    implementation(project(":domain"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)

    implementation("javax.inject:javax.inject:1")

    implementation(libs.coroutines)

    implementation(libs.datastore)

    implementation(libs.serialization)
}
