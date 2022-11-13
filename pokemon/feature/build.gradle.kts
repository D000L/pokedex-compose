applyComposeDependencies()
applyHiltDependencies()
applyLifecycleDependencies()
applyCoil()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":pokemon:destination"))
    implementation(project(":move:destination"))
    implementation(project(":navigation"))

    testImplementation(libs.junit)

    implementation(libs.hilt.navigation)

    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.pager)
}
android {
    namespace = "com.doool.pokedex.pokemon.feature"
}
