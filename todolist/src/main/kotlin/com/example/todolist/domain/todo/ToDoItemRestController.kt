package com.example.todolist.domain.todo

import com.example.todolist.domain.user.User
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todos")
class ToDoItemRestController(private val toDoItemService: ToDoItemService) {

    @ResponseStatus(OK)
    @GetMapping
    fun findAllToDos(@AuthenticationPrincipal user: User): List<ToDoItem> =
        toDoItemService.findToDoItems(user.identifier)

}