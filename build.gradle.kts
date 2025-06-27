group = "chan99k"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects{
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }
    dependencies {
        add("testImplementation", platform("org.junit:junit-bom:5.10.0"))
        add("testImplementation", "org.junit.jupiter:junit-jupiter")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}