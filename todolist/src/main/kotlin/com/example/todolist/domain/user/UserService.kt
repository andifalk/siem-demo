package com.example.todolist.domain.user

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class UserService(private val userEntityRepository: UserEntityRepository, private val passwordEncoder: PasswordEncoder) {

    fun findUserByUsername(username: String): User? = userEntityRepository.findOneByUsername(username)?.let {
        User(it.identifier, it.username, it.password, it.firstName, it.lastName, it.email, it.roles)
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun findUsers(): List<User> = userEntityRepository.findAll().map {
        User(it.identifier, it.username, it.password, it.firstName, it.lastName, it.email, it.roles)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    fun createUser(user: User): User =
        userEntityRepository.save(
            UserEntity(
                user.identifier,
                user.username,
                passwordEncoder.encode(user.password),
                user.firstName,
                user.lastName,
                user.email,
                user.roles
            )
        ).let { User(it.identifier, it.username, it.password, it.firstName, it.lastName, it.email, it.roles) }
}