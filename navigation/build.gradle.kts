applyComposeConfig()

dependencies {
    api(libs.compose.navigation)
    implementation(libs.compose.runtime)
}
android {
    namespace = "com.doool.pokedex.navigation"
}
