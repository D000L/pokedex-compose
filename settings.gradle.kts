pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "pokedex"
include(":domain")
include(":data")
include(":core")
include(":presentation")
include(":pokedex")
include(":widget")
include(":navigation")
include(":news:feature")
include(":news:destination")
include(":move:feature")
include(":move:destination")
include(":pokemon:feature")
include(":pokemon:destination")
include(":download:feature")
include(":download:destination")
