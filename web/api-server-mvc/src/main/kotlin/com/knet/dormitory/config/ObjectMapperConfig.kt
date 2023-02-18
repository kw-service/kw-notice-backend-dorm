package com.knet.dormitory.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        val module = JavaTimeModule()
        val localDateDeserializer = LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        module.addDeserializer(LocalDate::class.java, localDateDeserializer)
        objectMapper.registerModule(module)
        // if not write this, local date serialize to int array
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return objectMapper
    }
}