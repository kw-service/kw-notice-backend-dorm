dependencies{
    implementation(project(":dormitory-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1")
}

tasks.jar{
    enabled = false
}