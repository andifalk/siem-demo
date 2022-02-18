package com.example.todolist.domain.user

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users")
class UserRestController(private val userService: UserService) {

    @ResponseStatus(OK)
    @GetMapping
    fun allUsers() = userService.findUsers()

    @ResponseStatus(OK)
    @GetMapping("/me")
    fun authorizedUser(@AuthenticationPrincipal user: User): User? = userService.findUserByUsername(user.username)
}