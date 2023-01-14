dependencies{
    // reactor, webflux
    compileOnly("io.projectreactor.kotlin:reactor-kotlin-extensions")
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")

    compileOnly("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.springframework.boot:spring-boot-starter-data-r2dbc")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
    runtimeOnly("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")


    testCompileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testCompileOnly("io.mockk:mockk:1.13.2")
    testCompileOnly("org.springframework.boot:spring-boot-starter-test")
    testCompileOnly("io.projectreactor:reactor-test")
    testCompileOnly("io.kotest:kotest-runner-junit5:5.5.4")

    compileOnly("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
    compileOnly("com.google.firebase:firebase-admin:9.1.1")
    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}