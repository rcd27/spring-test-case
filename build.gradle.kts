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
    }
}

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"

    // Cloud Contract
    id("groovy")
    id("org.springframework.cloud.contract") version "2.2.3.RELEASE"

    // For publishing spring-cloud-contract stubs to local maven repository
    id("maven-publish")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-contract-dependencies:2.2.3.RELEASE")
    }
}

tasks.register("cleanRebuild") {
    exec {
        executable("./gradlew")
        args("clean", "api:assemble", "idgenerator:assemble", "approver:assemble", "mailer:assemble")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // Cloud Contract
    apply(plugin = "groovy")
    apply(plugin = "org.springframework.cloud.contract")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        // TODO: implement restdocs
        implementation("org.springframework.restdocs:spring-restdocs-webtestclient:2.0.4.RELEASE")
        implementation("org.springframework.kafka:spring-kafka:2.5.3.RELEASE")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.0.2.RELEASE")

        // help Jackson deserialize to Kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.1")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(module = "mockito-core")
        }
        testImplementation("io.projectreactor:reactor-test:3.3.7.RELEASE")
        testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:2.2.3.RELEASE")
        testImplementation("org.springframework.cloud:spring-cloud-contract-stub-runner:2.2.3.RELEASE")

        testImplementation("org.springframework.kafka:spring-kafka-test:2.5.3.RELEASE")

        testImplementation("io.mockk:mockk:1.10.0")
        testImplementation("com.ninja-squad:springmockk:2.0.0")

        testImplementation("com.google.truth:truth:1.0.1")

        testImplementation("org.codehaus.groovy:groovy-all:3.0.5")

        testImplementation("io.rest-assured:spring-web-test-client:4.3.1")
        testImplementation("io.rest-assured:rest-assured-common:4.3.1")

        implementation("com.vanniktech:gradle-maven-publish-plugin:0.12.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = URI("https://plugins.gradle.org/m2/")
        }
    }

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    contracts {
        setFailOnNoContracts(false)

        setTestMode("WebTestClient")
        setBaseClassForTests("")
    }
}
