package com.example.todolist.security

import com.example.todolist.security.metrics.MetricType
import com.example.todolist.security.metrics.MetricsEntity
import com.example.todolist.security.metrics.MetricsEntityRepository
import com.example.todolist.util.getLogger
import org.springframework.context.event.EventListener
import org.springframework.security.access.event.AuthorizationFailureEvent
import org.springframework.security.access.event.AuthorizedEvent
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class AuthenticationEventListener(private val metricsEntityRepository: MetricsEntityRepository) {

    private val log = getLogger()

    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent) {
        log.debug("Authentication Success: ${success.authentication.name}")
    }

    @EventListener
    @Transactional
    fun onFailure(failure: AbstractAuthenticationFailureEvent) {
        log.warn("Authentication Failure: ${failure.authentication.name}, error: ${failure.exception.message}")
        metricsEntityRepository.findOneByType(MetricType.AUTHENTICATION_FAILURE)?.let {
            it.count++
            metricsEntityRepository.save(it)
        } ?: metricsEntityRepository.save(MetricsEntity(UUID.randomUUID(), MetricType.AUTHENTICATION_FAILURE, 1))
    }

    @EventListener
    fun onAuthorizationSuccess(success: AuthorizedEvent) {
        log.debug("Authorization Success: ${success.authentication.name}")
    }

    @EventListener
    fun onAuthorizationFailure(failure: AuthorizationFailureEvent) {
        log.warn("Authorization Failure: ${failure.authentication.name}, config: ${failure.configAttributes} error: ${failure.accessDeniedException.message}")
        metricsEntityRepository.findOneByType(MetricType.AUTHORIZATION_FAILURE)?.let {
            it.count++
            metricsEntityRepository.save(it)
        } ?: metricsEntityRepository.save(MetricsEntity(UUID.randomUUID(), MetricType.AUTHORIZATION_FAILURE, 1))
    }

}