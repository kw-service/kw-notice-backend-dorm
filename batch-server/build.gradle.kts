plugins{
    id("org.jetbrains.kotlin.plugin.jpa") version "1.7.22"
}

dependencies{
    compileOnly("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("org.springframework.boot:spring-boot-starter-webflux:3.0.1")
    compileOnly("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
    compileOnly("org.springframework.boot:spring-boot-starter-batch:3.0.1")
    compileOnly("mysql:mysql-connector-java:8.0.30")
    runtimeOnly("com.mysql:mysql-connector-j")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa:3.0.1")

    implementation("org.hibernate.orm:hibernate-core:6.1.6.Final")
    implementation("org.jboss.logging:jboss-logging:3.5.0.Final")

    testCompileOnly("io.mockk:mockk:1.13.2")
    testCompileOnly("org.springframework.boot:spring-boot-starter-test")
    testCompileOnly("io.kotest:kotest-runner-junit5:5.5.4")
}

repositories {
    mavenCentral()
}

// jpa를 사용하기 위해서 강제로 class 를 open 할 필요가 있음
allOpen{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

// jpa를 사용하기 위해서 강제로 no arg constructor 를 생성할 필요가 있음
noArg{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.bootJar{
    enabled = true
}