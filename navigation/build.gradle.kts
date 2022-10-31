applyComposeConfig()

dependencies {
    api("androidx.navigation:navigation-compose:2.6.0-alpha03")

    implementation("androidx.compose.runtime:runtime:$compose_version")
}
android {
    namespace = "com.doool.pokedex.navigation"
}
