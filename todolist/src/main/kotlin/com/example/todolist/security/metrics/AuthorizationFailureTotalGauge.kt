package com.example.todolist.security.metrics

import com.example.todolist.security.metrics.MetricType.AUTHENTICATION_FAILURE
import com.example.todolist.security.metrics.MetricType.AUTHORIZATION_FAILURE
import com.example.todolist.util.getLogger
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct

internal const val AUTHORIZATION_FAILURES_TOTAL = "authorization.failures.total"

@Component
internal class AuthorizationFailureTotalGauge(
    private val meterRegistry: MeterRegistry,
    private val metricsEntityRepository: MetricsEntityRepository,
) {

    private val authorizationFailureCount = AtomicLong()
    private val log = getLogger()

    @PostConstruct
    fun bindToMeterRegistry() {
        meterRegistry.gauge(AUTHORIZATION_FAILURES_TOTAL, authorizationFailureCount)
    }

    @Scheduled(fixedDelayString = "\${metrics.scheduler.interval}")
    fun updateMetrics() {
        log.info("Updating metrics")
        metricsEntityRepository.findOneByType(AUTHORIZATION_FAILURE)?.let {
            authorizationFailureCount.set(it.count)
        }
    }
}
