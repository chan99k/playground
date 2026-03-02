plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(kotlin("test"))
}
