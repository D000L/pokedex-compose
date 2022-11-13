dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation(libs.coroutines)
}
android {
    namespace = "com.doool.pokedex.domain"
}
