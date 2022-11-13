applyComposeDependencies()
applyHiltDependencies()

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    testImplementation(libs.junit)

    implementation(libs.compose.activity)

    implementation(libs.hilt.navigation)
}
android {
    namespace = "com.doool.pokedex.download.feature"
}
