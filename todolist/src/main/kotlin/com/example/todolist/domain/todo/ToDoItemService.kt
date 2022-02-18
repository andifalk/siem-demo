package com.example.todolist.domain.todo

import com.example.todolist.domain.user.User
import com.example.todolist.domain.user.UserEntityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
@Service
class ToDoItemService(
    private val toDoItemEntityRepository: ToDoItemEntityRepository,
    private val userEntityRepository: UserEntityRepository
) {

    fun findToDoItems(userIdentifier: UUID): List<ToDoItem> =
        toDoItemEntityRepository.findAllByOwnerIdentifier(userIdentifier).map { toDoItemEntity ->
            ToDoItem(
                toDoItemEntity.identifier,
                toDoItemEntity.name,
                toDoItemEntity.dueDate,
                toDoItemEntity.owner.let { userEntity ->
                    User(
                        userEntity.identifier,
                        userEntity.username,
                        userEntity.password,
                        userEntity.firstName,
                        userEntity.lastName,
                        userEntity.email,
                        userEntity.roles)
                }
            )
        }

    fun findToDoItem(toDoItemIdentifier: UUID, userIdentifier: UUID): ToDoItem? =
        toDoItemEntityRepository.findOneByIdentifierAndOwnerIdentifier(toDoItemIdentifier, userIdentifier)?.let { toDoItemEntity ->
            ToDoItem(
                toDoItemEntity.identifier,
                toDoItemEntity.name,
                toDoItemEntity.dueDate,
                toDoItemEntity.owner.let {
                    User(it.identifier, it.username, it.password, it.firstName, it.lastName, it.email, it.roles)
                }
            )
        }

    @Transactional
    fun createToDoItem(toDoItem: ToDoItem, userIdentifier: UUID): ToDoItem? {
        return userEntityRepository.findOneByIdentifier(userIdentifier)?.let { user ->
            toDoItemEntityRepository.save(
                toDoItem.let { todo -> ToDoItemEntity(todo.identifier, todo.name, todo.dueDate, user) }
            ).let {
                ToDoItem(it.identifier, it.name, it.dueDate, it.owner.let {
                    userEntity -> User(
                        userEntity.identifier,
                        userEntity.username,
                        userEntity.password,
                        userEntity.firstName,
                        userEntity.lastName,
                        userEntity.email,
                        userEntity.roles)
                    }
                )
            }
        }
    }
}