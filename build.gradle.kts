import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
	kotlin("jvm") version "1.9.10"
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.spring") version "1.7.21"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.7.21"
}

group = "com.buffer"
version = "4.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations["annotationProcessor"])
	}
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
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")

	//javacord
	implementation("org.javacord:javacord:3.7.0")

	// To make jackson work with kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")

	// Unit conversion
	implementation("org.jscience:jscience:4.3.1")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")


	compileOnly("org.projectlombok:lombok")


	developmentOnly("org.springframework.boot:spring-boot-devtools")


	runtimeOnly("mysql:mysql-connector-java")


	//log4j
	runtimeOnly("org.apache.logging.log4j:log4j-core")


	// As if you're doing tests...
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
	useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}
