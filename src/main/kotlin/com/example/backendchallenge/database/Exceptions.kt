package com.example.backendchallenge.database

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String): Exception(message)

@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException::class)
  fun resourceNotFoundException(e: ResourceNotFoundException, request: WebRequest): ResponseEntity<String> {
    return ResponseEntity(e.message ?: "ResourceNotFoundException", HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler(Exception::class)
  fun globalExceptionHandler(e: Exception, request: WebRequest): ResponseEntity<String> {
    return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
  }
}