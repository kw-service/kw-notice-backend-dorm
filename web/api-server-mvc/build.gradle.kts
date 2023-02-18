dependencies{
    implementation(project(":dormitory-domain-notice"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
}

tasks.jar{
    enabled = false
}