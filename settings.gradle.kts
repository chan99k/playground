plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "playground"

include(
    "algorithm",

    "java",

    "kotlin",

    "spring",
)

project(":algorithm").projectDir = file("00-algorithm")
project(":java").projectDir = file("01-java")
project(":kotlin").projectDir = file("02-kotlin")
project(":spring").projectDir = file("03-spring")
