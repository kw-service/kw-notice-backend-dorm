<p align="center">
<img src="https://user-images.githubusercontent.com/72238126/180598251-9c2a726d-6618-45d6-8565-633290d395fa.png"/>
</p>

<h1 align="center">KW 알리미 Dormitory Server</h1>

<p align="center">
<img src="https://img.shields.io/badge/kotlin-1.7.22-A97BFF.svg"/>
<img src="https://img.shields.io/badge/gradle-7.6.1-5675DF.svg"/>
<img src="https://img.shields.io/badge/spring boot-3.0.1-32A579.svg"/>
<img src="https://img.shields.io/badge/license-Apache2.0-brightgreen.svg"/>
</p>

<p align="center">
This is a server that is monitoring university dormitory notices, using Spring MVC + Spring Batch(with Kotlin, Multi Module Project, Kotlin Gradle DSL).<br/>
I started the project to solve the problem that I feel uncomfortable living in the dormitory.
</p>

## Purpose
- Practice Kotlin
- Practice Spring Multi Module Project
- Implement DDD in Spring JPA, R2DBC

## Performance
- Show how implement multi module project with kotlin gradle DSL
- Show how to use r2dbc that make entity converter
- Show how to use JPA in Kotlin
- Monitoring dormitory notice per 1 min using Spring Batch
- Nondisruptive Deployment with Dockers Swarm

## Structure
#### Version 1
> ![dormitory-notice](https://user-images.githubusercontent.com/73744183/212556467-e71ed18b-f069-4136-ae58-317baea7223d.svg)

#### Version 2
> ![dormitory-notice-v2](https://user-images.githubusercontent.com/73744183/212954729-c7a57a11-43c6-40cd-b584-5043b7d8290b.svg)

#### Module Dependencies
> ![dormitory-notice-dependency drawio](https://user-images.githubusercontent.com/73744183/212954872-c1c268dd-7ce4-4559-8236-b5be0df8898b.svg)

#### Structure(with Docker Swarm)
> ![dormitory-infrastructure](https://user-images.githubusercontent.com/73744183/216516244-806fbede-576d-4306-82b7-f66c89303886.png)

## Skill Set

### Language
- kotlin(+coroutine)

### Backend
- Spring MVC
- Spring Batch
- Firebase Cloud Messaging
- Spring Data JPA(mysql, h2)
- Spring Webflux + R2DBC(abandon further development because batch system does not provide asynchronous)

### Infrastructure
- MySQL, H2

### Test
- mockk
- coroutine-test
- kotest
