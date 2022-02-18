package com.example.todolist.security.metrics

import org.springframework.data.jpa.repository.JpaRepository

interface MetricsEntityRepository : JpaRepository<MetricsEntity, Long> {

    fun findOneByType(type: MetricType): MetricsEntity?
}