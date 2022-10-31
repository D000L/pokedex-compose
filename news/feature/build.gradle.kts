applyComposeDependencies()
applyHiltDependencies()
applyCoil()

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    testImplementation("junit:junit:4.13.2")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
}
android {
    namespace = "com.doool.pokedex.news.feature"
}
