import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("com.bmuschko.docker-remote-api") version "6.4.0"
}

group = "com.github.rcd27"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:1.9")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.1")
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

    from("$buildDir/libs/${archiveName}") {
        rename { "spring-test-case.jar" }
    }

    from("src/main/docker")

    into("$buildDir/docker")
}

tasks.create("buildDockerImage", DockerBuildImage::class) {
    dependsOn.add("copyApp")
    inputDir.set(file("build/docker/"))

    images.add("github.com/rcd27/spring-test-case:latest")
}

// TODO: create task for pushing docker image
