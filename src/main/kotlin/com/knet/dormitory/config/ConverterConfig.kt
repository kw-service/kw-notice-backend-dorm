package com.knet.dormitory.config

import com.knet.dormitory.domain.notice.converter.NoticeReadConverter
import com.knet.dormitory.domain.notice.converter.NoticeWriterConvertor
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class ConverterConfig {
    @Bean
    fun r2dbcCustomConversions(databaseClient: DatabaseClient): R2dbcCustomConversions {
        val dialect = DialectResolver.getDialect(databaseClient.connectionFactory)
        val converters = ArrayList(dialect.converters)
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS)
        return R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters),
            getCustomConverters()
        )
    }

    // 추가하고 싶은 Converter collection 반환
    private fun getCustomConverters(): List<Converter<*, *>> {
        return listOf(NoticeReadConverter(), NoticeWriterConvertor())
    }
}