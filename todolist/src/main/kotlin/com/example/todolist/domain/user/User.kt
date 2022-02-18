package com.example.todolist.domain.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

data class User(val identifier: UUID,
                private val username: String,
                private val password: String,
                val firstName: String,
                val lastName: String,
                val email: String,
                @JsonIgnore val roles: List<String>
) : UserDetails {
    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        AuthorityUtils.commaSeparatedStringToAuthorityList(roles.map { "ROLE_$it" }.joinToString(","))

    @JsonIgnore
    override fun getPassword(): String = password

    override fun getUsername(): String = username

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isEnabled(): Boolean = true
}