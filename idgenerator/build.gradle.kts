apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
    plugin("groovy")
    plugin("maven-publish")
}

dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-contract-verifier:2.2.3.RELEASE")

    contracts {
        setBaseClassForTests("com.github.rcd27.idgenerator.BaseContractTest")
    }
}

group = "com.github.rcd27.idgenerator"
version = "0.0.1-SNAPSHOT"