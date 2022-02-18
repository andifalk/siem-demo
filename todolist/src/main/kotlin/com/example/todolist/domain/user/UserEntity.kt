package com.example.todolist.domain.user

import org.springframework.data.jpa.domain.AbstractPersistable
import java.util.UUID
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.FetchType.EAGER

@Entity
data class UserEntity(
    val identifier: UUID,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    @ElementCollection(fetch = EAGER) val roles: List<String>
) : AbstractPersistable<Long>()