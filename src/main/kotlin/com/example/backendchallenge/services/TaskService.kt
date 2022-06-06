package com.example.backendchallenge.services

import com.example.backendchallenge.domain.CreateTaskDto
import com.example.backendchallenge.domain.Task
import com.example.backendchallenge.domain.UpdateTaskDto
import com.example.backendchallenge.repositories.TaskRepository
import com.example.backendchallenge.repositories.findByIdOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.util.UUID

interface TaskService {

  suspend fun fetchAllTasks(orderBy: String = "createdAt"): List<Task>
  suspend fun fetchTask(id: UUID): Task?
  suspend fun createTask(createTask: CreateTaskDto): Task
  suspend fun updateTask(updateTask: UpdateTaskDto): Task?
  suspend fun deleteTask(id: UUID): Boolean
}

@Service
class TaskServiceImpl(
  private val taskSchedulerService: TaskSchedulerService,
  private val taskRepository: TaskRepository,
) : TaskService {

  override suspend fun fetchAllTasks(orderBy: String): List<Task> {
    return withContext(Dispatchers.IO) { taskRepository.findAllTasks(orderBy) }
  }

  override suspend fun fetchTask(id: UUID): Task? {
    return withContext(Dispatchers.IO) { taskRepository.findByIdOrNull(id) }
  }

  override suspend fun createTask(createTask: CreateTaskDto): Task {
    return taskSchedulerService.persistTask(createTask)
  }

  override suspend fun updateTask(updateTask: UpdateTaskDto): Task? {
    return withContext(Dispatchers.IO) {
      val fetchedTask = taskRepository.findByIdOrNull(updateTask.id) ?: return@withContext null
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
      taskRepository.save(updatedTask)
    }
  }

  override suspend fun deleteTask(id: UUID): Boolean {
    return withContext(Dispatchers.IO) {
      val task = taskRepository.findByIdOrNull(id) ?: return@withContext false
      taskRepository.delete(task)
      return@withContext true
    }
  }

}