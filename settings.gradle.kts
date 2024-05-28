pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "android-rasp"
include(":sample-app")
include(":rasp")
