applyComposeDependencies()

dependencies {
    implementation(project(":domain"))
    implementation(project(":navigation"))

    testImplementation(libs.junit)

    implementation(libs.accompanist.placeholder)
}
android {
    namespace = "com.doool.pokedex.core"
}
