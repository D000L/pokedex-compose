plugins {
    `kotlin-dsl` // enable the Kotlin-DSL
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("com.squareup:javapoet:1.13.0")

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
