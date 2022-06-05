package com.example.backendchallenge.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

interface TaskService {

  fun fetchAllTasks(orderBy: String = "createdAt"): List<Task>
  fun fetchTask(id: Long): Task
  suspend fun createTask(createTask: CreateTaskDto): Task
  fun updateTask(updateTask: UpdateTaskDto): Task
  fun deleteTask(id: Long)
}

@Service
class TaskServiceImpl : TaskService {

  @Autowired
  private lateinit var taskScheduler: TaskScheduler

  @Autowired
  private lateinit var taskRepository: TaskRepository

  override fun fetchAllTasks(orderBy: String): List<Task> {
    return taskRepository.findAll(Sort.by(orderBy))
  }

  override fun fetchTask(id: Long): Task {
    return taskRepository.findById(id).orElseThrow { ResourceNotFoundException("Unknown Task id: $id") }
  }

  override suspend fun createTask(createTask: CreateTaskDto): Task {
    return taskScheduler.persistTask(createTask)
  }

  override fun updateTask(updateTask: UpdateTaskDto): Task {
    val fetchedTask = taskRepository.findById(updateTask.id).orElseThrow { ResourceNotFoundException("Unknown Task id: ${updateTask.id}") }
    val currentTime = System.currentTimeMillis()
    val resolvedTime = if (updateTask.status == "resolved" && fetchedTask.status != "resolved") currentTime else null
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

  override fun deleteTask(id: Long) {
    val task = taskRepository.findById(id).orElseThrow { ResourceNotFoundException("Unknown Task id: $id") }
    taskRepository.delete(task)
  }

}