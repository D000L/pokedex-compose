applyComposeConfig()
applyHiltDependencies()
applyCoil()

dependencies {

    implementation(project(":domain"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)

    implementation("androidx.glance:glance-appwidget:1.1.1")
}
android {
    namespace = "com.doool.pokedex.widget"
}
