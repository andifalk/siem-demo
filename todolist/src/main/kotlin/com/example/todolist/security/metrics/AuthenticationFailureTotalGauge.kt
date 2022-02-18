package com.example.todolist.security.metrics

import com.example.todolist.security.metrics.MetricType.AUTHENTICATION_FAILURE
import com.example.todolist.util.getLogger
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct

internal const val AUTHENTICATION_FAILURES_TOTAL = "authentication.failures.total"

@Component
internal class AuthenticationFailureTotalGauge(
    private val meterRegistry: MeterRegistry,
    private val metricsEntityRepository: MetricsEntityRepository,
) {

    private val authenticationFailureCount = AtomicLong()
    private val log = getLogger()

    @PostConstruct
    fun bindToMeterRegistry() {
        meterRegistry.gauge(AUTHENTICATION_FAILURES_TOTAL, authenticationFailureCount)
    }

    @Scheduled(fixedDelayString = "\${metrics.scheduler.interval}")
    fun updateMetrics() {
        log.info("Updating metrics")
        metricsEntityRepository.findOneByType(AUTHENTICATION_FAILURE)?.let {
            authenticationFailureCount.set(it.count)
        }
    }
}
