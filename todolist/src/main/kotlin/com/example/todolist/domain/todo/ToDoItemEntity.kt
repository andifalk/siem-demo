package com.example.todolist.domain.todo

import com.example.todolist.domain.user.UserEntity
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.FetchType.EAGER
import javax.persistence.ManyToOne

@Entity
data class ToDoItemEntity(
    val identifier: UUID,
    val name: String,
    val dueDate: LocalDate,
    @ManyToOne(fetch = EAGER) val owner: UserEntity
) : AbstractPersistable<Long>()