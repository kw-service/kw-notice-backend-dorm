plugins{
    id("org.jetbrains.kotlin.plugin.jpa") version "1.7.22"
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.google.firebase:firebase-admin:9.1.1")
}

repositories {
    mavenCentral()
}

// class open
allOpen{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

// class no arg constructor
noArg{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}