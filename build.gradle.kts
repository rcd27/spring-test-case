import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("com.bmuschko.docker-remote-api") version "6.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

group = "com.github.rcd27"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven {
        url = URI("https://plugins.gradle.org/m2/")
        url = URI("https://dl.bintray.com/arrow-kt/arrow-kt/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    implementation("io.arrow-kt:arrow-core:0.10.4")
    implementation("io.arrow-kt:arrow-syntax:0.10.4")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

// Run Ktlint before every build
tasks.register("ktlint") {
    exec {
        executable = "./gradlew"
        args = listOf("ktlintFormat")
    }
}
tasks.getByName("compileKotlin") {
    dependsOn("ktlint")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

docker {
    url.set("unix:///var/run/docker.sock")
}

val archiveName = tasks.getByName("jar").property("archiveName")

tasks.create("copyApp", Copy::class.java) {
    dependsOn.add("build")

    from("$buildDir/libs/$archiveName") {
        rename { "spring-test-case.jar" }
    }

    from("src/main/docker")

    into("$buildDir/docker")
}

tasks.create("buildDockerImage", DockerBuildImage::class) {
    dependsOn.add("copyApp")
    inputDir.set(file("build/docker/"))

    images.add("registry.github.com/rcd27/spring-test-case:latest")
}

// TODO: create task for pushing docker image
