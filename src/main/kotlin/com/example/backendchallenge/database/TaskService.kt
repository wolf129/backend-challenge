package com.example.backendchallenge.database

import org.springframework.stereotype.Service

interface TaskService {

  suspend fun fetchAllTasks(orderBy: String = "createdAt"): List<Task>
  suspend fun fetchTask(id: Long): Task?
  suspend fun createTask(createTask: CreateTaskDto): Task
  suspend fun updateTask(updateTask: UpdateTaskDto): Task?
  suspend fun deleteTask(id: Long): Boolean
}

@Service
class TaskServiceImpl(
  private val taskSchedulerService: TaskSchedulerService,
  private val taskRepository: TaskRepository,
) : TaskService {

  override suspend fun fetchAllTasks(orderBy: String): List<Task> {
    return taskRepository.findAllTasks(orderBy)
  }

  override suspend fun fetchTask(id: Long): Task? {
    return taskRepository.findById(id)
  }

  override suspend fun createTask(createTask: CreateTaskDto): Task {
    return taskSchedulerService.persistTask(createTask)
  }

  override suspend fun updateTask(updateTask: UpdateTaskDto): Task? {
    val fetchedTask = taskRepository.findById(updateTask.id) ?: return null
    val currentTime = System.currentTimeMillis()
    val resolvedTime = if (updateTask.status == "resolved" && fetchedTask.status != "resolved") {
      currentTime
    } else {
      null
    }
    val updatedTask = fetchedTask.copy(
      updatedAt = currentTime,
      dueDate = updateTask.dueDate,
      title = updateTask.title,
      description = updateTask.description,
      priority = updateTask.priority,
      status = updateTask.status,
      resolvedAt = resolvedTime,
    )
    return taskRepository.save(updatedTask)
  }

  override suspend fun deleteTask(id: Long): Boolean {
    val task = taskRepository.findById(id) ?: return false
    taskRepository.delete(task)
    return true
  }

}