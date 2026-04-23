plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "playground"

// 00-algorithm
include("algorithm", "algorithm:solve-java", "algorithm:solve-kotlin")
project(":algorithm").projectDir = file("00-algorithm")
project(":algorithm:solve-java").projectDir = file("00-algorithm/solve-java")
project(":algorithm:solve-kotlin").projectDir = file("00-algorithm/solve-kotlin")

// 01-java
include("java")
project(":java").projectDir = file("01-java")

// 02-kotlin
include("kotlin")
project(":kotlin").projectDir = file("02-kotlin")

// 03-spring
include("spring")
project(":spring").projectDir = file("03-spring")
