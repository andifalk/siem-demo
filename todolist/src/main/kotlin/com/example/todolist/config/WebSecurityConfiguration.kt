package com.example.todolist.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class WebSecurityConfiguration {

    @Configuration
    class APiSecurityConfiguration : WebSecurityConfigurerAdapter() {
        private val actuatorRole = "ACTUATOR"
        private val prometheusRole = "PROMETHEUS"

        override fun configure(http: HttpSecurity) {
            http {
                csrf { disable() }
                authorizeRequests {
                    authorize(EndpointRequest.to(InfoEndpoint::class.java, HealthEndpoint::class.java), permitAll)
                    authorize(EndpointRequest.to("prometheus"), permitAll)
                    authorize(EndpointRequest.toAnyEndpoint(), hasRole(actuatorRole))
                    authorize("/v3/**", permitAll)
                    authorize("/swagger-ui.html", permitAll)
                    authorize(anyRequest, authenticated)
                }
                httpBasic { }
                formLogin { }
            }
        }
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun authenticationEventPublisher
                (applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(applicationEventPublisher)
    }

}