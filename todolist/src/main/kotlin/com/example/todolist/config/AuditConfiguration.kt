package com.example.todolist.config

import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository
import org.springframework.boot.actuate.trace.http.HttpTraceRepository
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuditConfiguration {

    @Bean
    fun auditEvent(): AuditEventRepository = InMemoryAuditEventRepository()

    @Bean
    fun httpTrace(): HttpTraceRepository = InMemoryHttpTraceRepository()

}