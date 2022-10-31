applyComposeDependencies()
applyHiltDependencies()
applyLifecycleDependencies()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":pokemon:destination"))
    implementation(project(":move:destination"))
    implementation(project(":navigation"))

    testImplementation("junit:junit:4.13.2")

    implementation("io.coil-kt:coil-compose:1.3.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("com.google.accompanist:accompanist-flowlayout:$accompanist_version")
    implementation("com.google.accompanist:accompanist-pager:$accompanist_version")
}
