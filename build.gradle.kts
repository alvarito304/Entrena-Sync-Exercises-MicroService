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

    // auth para subir videos a youtube
    // https://mvnrepository.com/artifact/com.google.apis/google-api-services-youtube
    implementation("com.google.apis:google-api-services-youtube:v3-rev20250422-2.0.0")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    implementation("com.google.api-client:google-api-client:2.7.2")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.34.0")
    //implementation("com.google.oauth-client:google-oauth-client:1.34.1")

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
    implementation("org.springframework.boot:spring-boot-starter-web"){
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-security")
    }

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
