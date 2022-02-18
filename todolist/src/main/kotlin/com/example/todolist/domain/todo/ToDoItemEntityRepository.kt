package com.example.todolist.domain.todo

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ToDoItemEntityRepository : JpaRepository<ToDoItemEntity, Long> {
    fun findOneByIdentifierAndOwnerIdentifier(identifier: UUID, userIdentifier: UUID): ToDoItemEntity?
    fun findAllByOwnerIdentifier(userIdentifier: UUID): List<ToDoItemEntity>
}