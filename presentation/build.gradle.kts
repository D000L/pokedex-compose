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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")

    testImplementation("junit:junit:4.13.2")
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("androidx.activity:activity-compose:$compose_version")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha03")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("com.google.accompanist:accompanist-navigation-material:$accompanist_version")
}
android {
    namespace = "com.doool.pokedex.presentation"
}
