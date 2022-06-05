package com.example.backendchallenge.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class TaskController {
  @Autowired
  private lateinit var taskService: TaskService

  @GetMapping("/tasks/{orderBy}", "/tasks", params = ["orderBy"])
  @ResponseStatus(HttpStatus.OK)
  suspend fun fetchAllTasks(
    @PathVariable(required = false) orderBy: String?,
  ): List<TaskDto> {
    val tasks = taskService.fetchAllTasks(orderBy ?: "createdAt")
    return tasks.map { it.toTaskDto() }
  }

  @GetMapping("/tasks/{id}", params = ["id"])
  @ResponseStatus(HttpStatus.OK)
  suspend fun fetchTask(
    @PathVariable id: Long,
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

  @PutMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  suspend fun updateTask(
    @RequestBody updateTask: UpdateTaskDto,
  ): ResponseEntity<TaskDto> {
    val updatedTask = taskService.updateTask(updateTask) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    return ResponseEntity.ok().body(updatedTask.toTaskDto())
  }

  @DeleteMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  suspend fun deleteTask(
    @PathVariable id: Long,
  ): ResponseEntity<Void> {
    val successFull = taskService.deleteTask(id)
    return if (successFull) {
      ResponseEntity(HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

}