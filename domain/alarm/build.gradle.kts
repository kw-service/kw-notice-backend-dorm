plugins{
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.20"
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.google.firebase:firebase-admin:9.1.1")
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