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

rootProject.name = "Lesson4"
include(":app")
include(":cryptoloader")
include(":datathread")
include(":looper")
include(":serviceapp")
include(":thread")
include(":workmanager")
