dependencies{
    implementation(project(":dormitory-domain-notice"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
}

tasks.jar{
    enabled = false
}