rootProject.name = "dokotlin"
include("yolo-domain")
include("yolo-api")

pluginManagement {

    repositories {
        gradlePluginPortal()
    }

    val pluginVersions = mapOf(
        "org.jetbrains.kotlin" to "1.3.71",
        "org.jetbrains.kotlin.plugin" to "1.3.71",
        "org.springframework" to "2.3.0.RELEASE",
        "io.spring" to "1.0.9.RELEASE"
    )

    resolutionStrategy {
        eachPlugin {
            if (pluginVersions.containsKey(requested.id.namespace)) {
                useVersion(pluginVersions[requested.id.namespace])
            }
        }
    }
}
