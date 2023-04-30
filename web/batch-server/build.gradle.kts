dependencies{
    implementation(project(":dormitory-domain-alarm"))
    implementation(project(":dormitory-domain-notice"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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