// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // Because of IDE bug https://youtrack.jetbrains.com/issue/KTIJ-19370
plugins {
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.android.library.get().pluginId) apply false
    id(libs.plugins.kotlin.android.get().pluginId) apply false
    alias(libs.plugins.hilt) apply false

    id("com.rickbusarow.module-check") version "0.12.5"
}

buildscript {
    repositories {
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        // gradlew generateProjectDependencyGraph
        classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:0.9.0-SNAPSHOT")
    }
}

apply(plugin="com.vanniktech.dependency.graph.generator")

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    applyPlugin()
}
