plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "playground"

include(
    "algorithm",
    "algorithm:java",
    "algorithm:kotlin",

    "java",
    "java:effective-java",
    "java:effective-java:ch02-builder",

    "kotlin",

    "spring",
    "spring:modulith",
)

project(":algorithm").projectDir = file("00-algorithm")
project(":algorithm:java").projectDir = file("00-algorithm/java")
project(":algorithm:kotlin").projectDir = file("00-algorithm/kotlin")
project(":java").projectDir = file("01-java")
project(":java:effective-java").projectDir = file("01-java/effective-java")
project(":java:effective-java:ch02-builder").projectDir = file("01-java/effective-java/ch02-builder")
project(":kotlin").projectDir = file("02-kotlin")
project(":spring").projectDir = file("03-spring")
project(":spring:modulith").projectDir = file("03-spring/modulith")
