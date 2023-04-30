plugins{
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.20"
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

repositories {
    mavenCentral()
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}