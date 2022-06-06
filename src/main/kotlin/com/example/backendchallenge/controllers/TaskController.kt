package com.example.backendchallenge.controllers

import com.example.backendchallenge.domain.CreateTaskDto
import com.example.backendchallenge.domain.TaskDto
import com.example.backendchallenge.domain.UpdateTaskDto
import com.example.backendchallenge.services.TaskService
import com.example.backendchallenge.services.toTaskDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class TaskController(
  private val taskService: TaskService
) {

  @GetMapping("/tasks", "/tasks/orderBy={orderBy}")
  @ResponseStatus(HttpStatus.OK)
  suspend fun fetchAllTasks(
    @PathVariable(required = false) orderBy: String?,
  ): List<TaskDto> {
    val tasks = taskService.fetchAllTasks(orderBy ?: "createdAt")
    return tasks.map { it.toTaskDto() }
  }

  @GetMapping("/tasks/id={id}")
  suspend fun fetchTask(
    @PathVariable id: UUID,
  ): ResponseEntity<TaskDto> {
    val task = taskService.fetchTask(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok().body(task.toTaskDto())
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  suspend fun createTask(
    @RequestBody createTask: CreateTaskDto,
  ): TaskDto {
    val savedTask = taskService.createTask(createTask)
    return savedTask.toTaskDto()
  }

  @PutMapping("/tasks")
  suspend fun updateTask(
    @RequestBody updateTask: UpdateTaskDto,
  ): ResponseEntity<TaskDto> {
    val updatedTask = taskService.updateTask(updateTask) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok().body(updatedTask.toTaskDto())
  }

  @DeleteMapping("/tasks/id={id}")
  suspend fun deleteTask(
    @PathVariable id: UUID,
  ): ResponseEntity<Void> {
    val successFull = taskService.deleteTask(id)
    return if (successFull) {
      ResponseEntity(HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

}