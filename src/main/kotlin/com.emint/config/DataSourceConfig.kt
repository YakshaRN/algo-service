package com.emint.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    @Primary
    fun jdbcTemplate(primaryDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(primaryDataSource)
    }

    @Bean
    fun secondaryJdbcTemplate(@Qualifier("secondaryDataSource") ds: DataSource): JdbcTemplate {
        return JdbcTemplate(ds)
    }
}
