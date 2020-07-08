import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

group = "com.rcd27"
version = "0.0.1-SNAPSHOT"

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        val kotlinVersion = "1.3.72"
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "kotlinx-serialization")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-amqp")
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

        implementation("io.arrow-kt:arrow-core:0.10.4")
        implementation("io.arrow-kt:arrow-syntax:0.10.4")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.0.2.RELEASE")


        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(module = "mockito-core")
        }
        testImplementation("io.projectreactor:reactor-test:3.3.7.RELEASE")
        testImplementation("org.springframework.amqp:spring-rabbit-test")
        testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:2.2.3.RELEASE")

        testImplementation("io.mockk:mockk:1.10.0")
        testImplementation("com.ninja-squad:springmockk:2.0.0")

        testImplementation("com.google.truth:truth:1.0.1")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = URI("https://plugins.gradle.org/m2/")
            url = URI("https://dl.bintray.com/arrow-kt/arrow-kt/")
        }
    }

    java.sourceCompatibility = JavaVersion.VERSION_11

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}
