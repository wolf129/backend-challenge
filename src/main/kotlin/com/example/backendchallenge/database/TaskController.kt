package com.example.backendchallenge.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class TaskController {
  @Autowired
  private lateinit var taskService: TaskService

//  @Autowired
//  private lateinit var modelMapper: ModelMapper

  @GetMapping("/task-list/{orderBy}", "/task-list")
  @ResponseStatus(HttpStatus.OK)
  fun fetchAllTasks(
    @PathVariable(required = false) orderBy: String?,
  ): List<TaskDto> {
    val tasks = taskService.fetchAllTasks(orderBy?: "createdAt")
    return tasks.map(::mapToDto)
  }

  @GetMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun fetchTask(
    @PathVariable id: Long,
  ): TaskDto {
    val task = taskService.fetchTask(id)
    return mapToDto(task)
  }

  @PostMapping("/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  suspend fun createTask(
    @RequestBody task: TaskDto,
  ): TaskDto {
    val savedTask = taskService.createTask(mapFromDto(task))
    return mapToDto(savedTask)
  }

  @PutMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun updateTask(
    @PathVariable id: Long,
    @RequestBody task: TaskDto,
  ) :TaskDto {
    val updatedTask = taskService.updateTask(id, mapFromDto(task))
    return mapToDto(updatedTask)
  }

  @DeleteMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun deleteTask(
    @PathVariable id: Long,
  ): Map<String, Boolean> {
    taskService.deleteTask(id)
    return mapOf("deleted" to true)
  }

  private fun mapToDto(task: Task): TaskDto {
    val (id, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status) = task
    return TaskDto(id!!, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
  }

  private fun mapFromDto(taskDto: TaskDto): Task {
    val (id, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status) = taskDto
    return Task(id, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
  }

}