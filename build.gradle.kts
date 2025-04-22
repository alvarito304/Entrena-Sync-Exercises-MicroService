plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "EntrenaSync"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // Logback por defecto
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.slf4j:slf4j-api:2.0.9")

    //mongodb
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //api rest
    implementation("org.springframework.boot:spring-boot-starter-web")

    //serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")


    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
