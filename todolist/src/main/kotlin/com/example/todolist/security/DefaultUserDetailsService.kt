package com.example.todolist.security

import com.example.todolist.domain.user.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DefaultUserDetailsService(private val userService: UserService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userService.findUserByUsername(username) ?: throw UsernameNotFoundException("No user found for $username")

}