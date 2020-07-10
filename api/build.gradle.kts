apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}

group = "com.github.rcd27.api"
version = "0.0.1-SNAPSHOT"