apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
}

dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:2.2.3.RELEASE")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:2.2.3.RELEASE")
}


group = "com.github.rcd27.api"
version = "0.0.1-SNAPSHOT"