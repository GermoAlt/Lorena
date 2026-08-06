import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    kotlin("jvm") version "2.4.10"
    id("org.springframework.boot") version "4.1.0"
    id("org.jetbrains.kotlin.plugin.spring") version "2.4.10"
}

group = "com.buffer"
version = "4.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src/main/kotlin", "src/main/java"))
    }
}

springBoot {
    mainClass.set("com.buffer.lorena.LorenaApplicationKt")
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    annotationProcessor(platform(SpringBootPlugin.BOM_COORDINATES))
    developmentOnly(platform(SpringBootPlugin.BOM_COORDINATES))
    testImplementation(platform(SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    // RestTemplateBuilder moved into its own module in Boot 4
    implementation("org.springframework.boot:spring-boot-starter-restclient")

    //javacord
    implementation("org.javacord:javacord:3.7.0")

    // To make jackson work with kotlin
    implementation("tools.jackson.module:jackson-module-kotlin")

    // Unit conversion
    implementation("org.jscience:jscience:4.3.1")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


    developmentOnly("org.springframework.boot:spring-boot-devtools")


    //log4j
    runtimeOnly("org.apache.logging.log4j:log4j-core")


    // As if you're doing tests...
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    // LorenaApplicationTests declares no @Test methods; Gradle 9 fails the task for that by default.
    failOnNoDiscoveredTests = false
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
