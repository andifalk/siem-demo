package com.example.todolist.security.metrics

import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated

@Entity
data class MetricsEntity(
    val identifier: UUID,
    @Enumerated(STRING) val type: MetricType,
    var count: Long
) : AbstractPersistable<Long>()