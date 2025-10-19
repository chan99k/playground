plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "playground"
include("effective-java")
include("spring-dgs")
include("algorithm")
include("without-springboot")
include("toby-spring")
include("spring-security")
include("sakila-mysql")