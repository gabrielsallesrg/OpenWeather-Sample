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
    }
}

rootProject.name = "OpenWeather Sample"
include(":app")
include(":feature:forecast")
include(":data:forecast")
include(":domain:forecast")
include(":data:common_remote")
include(":data:database")
include(":design")
include(":helper")
include(":helper:test")
