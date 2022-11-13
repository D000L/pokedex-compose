applyComposeDependencies()
applyHiltDependencies()
applyCoil()

dependencies {

    implementation(project(":domain"))

    implementation(project(":core"))
    implementation(project(":navigation"))
    implementation(project(":news:feature"))
    implementation(project(":news:destination"))
    implementation(project(":pokemon:feature"))
    implementation(project(":pokemon:destination"))
    implementation(project(":move:feature"))
    implementation(project(":move:destination"))
    implementation(project(":download:destination"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    implementation(libs.splashscreen)

    implementation(libs.compose.activity)

    implementation(libs.hilt.navigation)
    implementation(libs.compose.navigation)

    implementation(libs.palette)

    implementation(libs.accompanist.navigation)
}
android {
    namespace = "com.doool.pokedex.presentation"
}
