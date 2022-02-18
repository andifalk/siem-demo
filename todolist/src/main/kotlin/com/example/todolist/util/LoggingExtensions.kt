package com.example.todolist.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Gets or creates the logger instance for this class.
 *
 * This extension function can be within any class to get that class' logger instance.
 * It is intended to be used for logger field initialisation:
 *
 * ```
 * private val log: Logger = getLogger()
 * ```
 */
fun Any.getLogger(): Logger = LoggerFactory.getLogger(this.javaClass)

