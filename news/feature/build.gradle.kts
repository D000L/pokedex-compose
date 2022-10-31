applyComposeDependencies()
applyHiltDependencies()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    testImplementation("junit:junit:4.13.2")

    implementation("io.coil-kt:coil-compose:1.3.2")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
}
