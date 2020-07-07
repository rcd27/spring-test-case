apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
    plugin("groovy")
}

dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-contract-verifier:2.2.3.RELEASE")
}

group = "com.github.rcd27.idgenerator"
version = "0.0.1-SNAPSHOT"