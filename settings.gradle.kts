plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "playground"

apply(from = "00-algorithm/modules.gradle.kts")
apply(from = "01-java/modules.gradle.kts")
apply(from = "02-kotlin/modules.gradle.kts")
apply(from = "03-spring/modules.gradle.kts")
