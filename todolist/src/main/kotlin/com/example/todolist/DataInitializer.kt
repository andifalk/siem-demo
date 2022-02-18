package com.example.todolist

import com.example.todolist.domain.todo.ToDoItem
import com.example.todolist.domain.todo.ToDoItemEntity
import com.example.todolist.domain.todo.ToDoItemEntityRepository
import com.example.todolist.domain.todo.ToDoItemService
import com.example.todolist.domain.user.User
import com.example.todolist.domain.user.UserEntity
import com.example.todolist.domain.user.UserEntityRepository
import com.example.todolist.domain.user.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
class DataInitializer(
    private val userEntityRepository: UserEntityRepository,
    private val toDoItemEntityRepository: ToDoItemEntityRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val user: UserEntity = userEntityRepository.save(
            UserEntity(UUID.randomUUID(), "user", passwordEncoder.encode("secret"),
                "Ute", "User", "ute.user@example.com", listOf("USER")))

        val admin: UserEntity = userEntityRepository.save(
            UserEntity(UUID.randomUUID(), "admin", passwordEncoder.encode("admin"),
                "Axel", "Admin", "axel.admin@example.com", listOf("USER", "ADMIN")))

        val actuator: UserEntity = userEntityRepository.save(
            UserEntity(UUID.randomUUID(), "actuator", passwordEncoder.encode("actuator"),
                "Anton", "Actuator", "anton.actuator@example.com", listOf("ACTUATOR")))

        val prometheus: UserEntity = userEntityRepository.save(
            UserEntity(UUID.randomUUID(), "prometheus", passwordEncoder.encode("prometheus"),
                "Peter", "Prometheus", "peter.prometheus@example.com", listOf("PROMETHEUS")))

        toDoItemEntityRepository.save(
            ToDoItemEntity(UUID.randomUUID(), "Einkaufen", LocalDate.now().plusDays(1), user)
        )
        toDoItemEntityRepository.save(
            ToDoItemEntity(UUID.randomUUID(), "Arzttermin", LocalDate.now().plusDays(5), user)
        )
    }
}