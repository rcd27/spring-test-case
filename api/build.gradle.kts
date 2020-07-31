apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}

// FIXME stub-runner: if run api:test task without publishing `idgenerator` stubs to maven local repo - tests are broken

group = "com.github.rcd27.api"
version = "0.0.1-SNAPSHOT"