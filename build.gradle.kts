// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val gradleVersionPlugin = "0.43.0"

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        classpath("com.autonomousapps:dependency-analysis-gradle-plugin:1.2.1")

        classpath("com.github.ben-manes:gradle-versions-plugin:$gradleVersionPlugin")
    }
}

apply(plugin = "com.autonomousapps.dependency-analysis")

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    applyPlugin()
}
