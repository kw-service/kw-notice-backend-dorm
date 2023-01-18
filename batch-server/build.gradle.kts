dependencies{
    implementation(project(":dormitory-domain"))
    implementation("org.springframework.boot:spring-boot-starter-batch:3.0.1")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
}

repositories {
    mavenCentral()
}

tasks.bootJar{
    enabled = true
}

tasks.jar{
    enabled = false
}