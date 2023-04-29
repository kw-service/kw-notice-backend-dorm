dependencies{
    implementation(project(":dormitory-domain-alarm"))
    implementation(project(":dormitory-domain-notice"))
    implementation("org.springframework.boot:spring-boot-starter-batch:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.1")
    testImplementation("org.springframework.batch:spring-batch-test:5.0.1")
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