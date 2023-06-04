plugins {
    `kotlin-dsl` // enable the Kotlin-DSL
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    implementation("com.android.tools.build:gradle:8.0.2")
    implementation("com.squareup:javapoet:1.13.0")

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
