applyComposeDependencies()
applyHiltDependencies()
applyLifecycleDependencies()
applyCoil()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(project(":move:destination"))
    implementation(project(":navigation"))

    testImplementation(libs.junit)

    implementation(libs.hilt.navigation)
}
android {
    namespace = "com.doool.pokedex.move.feature"
}
