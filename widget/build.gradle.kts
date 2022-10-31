applyComposeConfig()
applyHiltDependencies()
applyCoil()

dependencies {

    implementation(project(":domain"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")
}
android {
    namespace = "com.doool.pokedex.widget"
}
