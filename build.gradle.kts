import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    kotlin("plugin.jpa") version "1.8.20"
    jacoco
}

allprojects {
    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("jacoco")
    }

    tasks.jacocoTestReport{
        tasks.clean
        reports{
            html.outputLocation.set(file("$rootDir/build/reports/jacoco/html"))
        }
    }

    group = "com.knet"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final")

        implementation("com.mysql:mysql-connector-j:8.0.33")
        testImplementation("io.mockk:mockk:1.13.2")
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.bootJar {
    enabled = false
}
