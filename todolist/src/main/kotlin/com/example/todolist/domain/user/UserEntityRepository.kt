package com.example.todolist.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun findOneByUsername(username: String): UserEntity?
    fun findOneByIdentifier(identifier: UUID): UserEntity?
}