applyComposeDependencies()
applyHiltDependencies()
applyCoil()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    testImplementation(libs.junit)

    implementation(libs.hilt.navigation)

    implementation(libs.compose.paging)
}
android {
    namespace = "com.doool.pokedex.news.feature"
}
