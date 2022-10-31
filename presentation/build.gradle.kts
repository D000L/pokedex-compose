applyComposeDependencies()
applyHiltDependencies()

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
    implementation(project(":download:feature"))
    implementation(project(":download:destination"))

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")

    implementation("androidx.activity:activity-compose:$compose_version")

    implementation("io.coil-kt:coil-compose:1.3.2")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.5.0-beta01")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("com.google.accompanist:accompanist-navigation-material:$accompanist_version")
}
