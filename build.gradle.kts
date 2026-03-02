plugins {
    java
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.spring.boot) apply false
}

val containerModules = setOf(":algorithm", ":java", ":kotlin", ":spring")

allprojects {
    group = "dev.chan99k"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    if (path !in containerModules) {
        apply(plugin = "java")

        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }
        }

        tasks.test {
            useJUnitPlatform()
        }
    }
}
