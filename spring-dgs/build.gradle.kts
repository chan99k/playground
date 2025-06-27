plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("com.netflix.dgs.codegen") version "7.0.3"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

val netflixDgsVersion by extra("10.0.1")

group = "chan99k"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}


repositories {
    mavenCentral()
}

allOpen {
    annotations("jakarta.persistence.Entity")
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars") // 미리 등록된 자주 사용하는 scalars 를 등록하도록 함
    implementation("name.nkonev.multipart-spring-graphql:multipart-spring-graphql:1.5.3")
    implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-gradle:7.0.3")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-micrometer")

    implementation("com.github.ben-manes.caffeine:caffeine:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.4.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.generateJava {
    language = "kotlin"
    packageName = "com.chan99k.springdgs"
    typeMapping = mutableMapOf(
        "ID" to "Long",
        "Email" to "String"
    )
    generateClientv2 = true
}

dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:$netflixDgsVersion")
    }
}
