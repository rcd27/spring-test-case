apply {
  plugin("io.spring.dependency-management")
  plugin("org.springframework.boot")
  plugin("maven-publish")
}

tasks.getByName("assemble") {
  dependsOn("publishToMavenLocal")
}

group = "com.github.rcd27.approver"
version = "0.0.1-SNAPSHOT"