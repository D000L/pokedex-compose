applyComposeDependencies()

dependencies {
    implementation(project(":domain"))
    implementation(project(":navigation"))

    testImplementation("junit:junit:4.13.2")

    implementation("com.google.accompanist:accompanist-placeholder:$accompanist_version")
}
android {
    namespace = "com.doool.pokedex.core"
}
