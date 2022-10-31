applyComposeDependencies()
applyHiltDependencies()

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    testImplementation("junit:junit:4.13.2")

    implementation("androidx.activity:activity-compose:$compose_version")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}
android {
    namespace = "com.doool.pokedex.download.feature"
}
