package com.example.backendchallenge.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
  ): TaskDto {
    val task = taskService.fetchTask(id)
    return task.toTaskDto()
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
  ): TaskDto {
    val updatedTask = taskService.updateTask(updateTask)
    return updatedTask.toTaskDto()
  }

  @DeleteMapping("/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  suspend fun deleteTask(
    @PathVariable id: Long,
  ) {
    taskService.deleteTask(id)
  }

  private fun Task.toTaskDto(): TaskDto {
    return TaskDto(id!!, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
  }

  private fun TaskDto.toTask(): Task {
    return Task(id, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
  }

  private fun CreateTaskDto.toTask(): Task {
    val time = System.currentTimeMillis()
    return Task(
      id = null,
      createdAt = time,
      updatedAt = time,
      dueDate = dueDate,
      resolvedAt = null,
      title = title,
      description = description,
      priority = priority,
      status = "created"
    )
  }

}