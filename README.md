<p align="center">
<img src="https://user-images.githubusercontent.com/72238126/180598251-9c2a726d-6618-45d6-8565-633290d395fa.png"/>
</p>

<h1 align="center">KW 알리미 Dormitory Server</h1>

<p align="center">
<img src="https://img.shields.io/badge/kotlin-1.8.20-A97BFF.svg"/>
<img src="https://img.shields.io/badge/gradle-7.6.1-5675DF.svg"/>
<img src="https://img.shields.io/badge/spring boot-3.0.6-32A579.svg"/>
<img src="https://img.shields.io/badge/license-Apache2.0-brightgreen.svg"/>
</p>

<p align="center">
This is a server that is monitoring university dormitory notices, using Spring MVC + Spring Batch(with Kotlin, Multi Module Project, Kotlin Gradle DSL).<br/>
I started the project to solve the problem that I feel uncomfortable living in the dormitory.
</p>

## Purpose
- To solve the problem that I feel uncomfortable living in the dormitory
- To learn how to use Spring Batch, Spring JPA(with Kotlin)
- To learn how to use Kotlin Gradle DSL
- To learn Gradle multi-module project with Kotlin

## Performance
- Implement multi-module project with kotlin gradle DSL
- Implement asynchronous logic with Kotlin coroutine to reduce time by up to 20x
- Test code coverage using Jacoco is over 90 percent
- Monitoring dormitory notice per 1 min using Spring Batch
- Use Docker Swarm to build nondisruptive servers that don't stop servers during updates

## Process Flow
<p align="center">
<img src="https://user-images.githubusercontent.com/73744183/235336199-916794aa-dba7-4bd0-b152-4f5580f6416a.svg">
</p>

## Structure
#### version 1
<p align="center">
<img src="https://user-images.githubusercontent.com/73744183/212556467-e71ed18b-f069-4136-ae58-317baea7223d.svg">
</p>

#### version 2
<p align="center">
<img src="https://user-images.githubusercontent.com/73744183/212954729-c7a57a11-43c6-40cd-b584-5043b7d8290b.svg">
</p>

#### Module Dependencies
<p align="center">
<img src="https://user-images.githubusercontent.com/73744183/212954872-c1c268dd-7ce4-4559-8236-b5be0df8898b.svg">
</p>

#### Structure(with Docker Swarm)
<p align="center">
<img src="https://user-images.githubusercontent.com/73744183/216516244-806fbede-576d-4306-82b7-f66c89303886.png">
</p>

## Skill Set

### Language
- Kotlin(+Coroutine)

### Backend
- Spring MVC
- Spring Batch
- Spring Data JPA(mysql, h2)
- Spring Webflux(for webclient)

### Third-Party
- Firebase Cloud Messaging

### Infrastructure
- MySQL, H2

### Test
- mockk
- coroutine-test
- kotest
