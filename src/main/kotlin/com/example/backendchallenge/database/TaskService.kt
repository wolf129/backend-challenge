package com.example.backendchallenge.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

interface TaskService {

  fun fetchAllTasks(orderBy: String = "createdAt"): List<Task>
  fun fetchTask(id: Long): Task
  suspend fun createTask(task: Task): Task
  fun updateTask(id: Long, task: Task): Task
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

  override suspend fun createTask(task: Task): Task {
    return taskScheduler.persistTask(task)
  }

  override fun updateTask(id: Long, task: Task): Task {
    val fetchedTask = taskRepository.findById(id).orElseThrow { ResourceNotFoundException("Unknown Task id: $id") }
    val updatedTask = fetchedTask.copy(
      updatedAt = System.currentTimeMillis(),
      dueDate = task.dueDate,
      title = task.title,
      description = task.description,
      priority = task.priority,
      status = task.status,
      resolvedAt = task.resolvedAt,
    )
    taskRepository.save(updatedTask)
    return updatedTask
  }

  override fun deleteTask(id: Long) {
    val task = taskRepository.findById(id).orElseThrow { ResourceNotFoundException("Unknown Task id: $id") }
    taskRepository.delete(task)
  }

}