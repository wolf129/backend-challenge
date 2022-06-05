package com.example.backendchallenge.database

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TaskScheduler {

  @Autowired
  private lateinit var taskRepository: TaskRepository

  // Concurrency
  private val mutex = Mutex()
  private var taskGeneratorJob: Job? = null

  private fun generateTask(): Task {
    val currentTime = System.currentTimeMillis()
    return Task(
      createdAt = currentTime,
      updatedAt = currentTime,
      dueDate = currentTime + Random.nextLong(592_000_000L, 2_592_000_000L),
      title = "Task created by scheduler at: $currentTime",
      priority = Random.nextInt(0, 20),
      status = "created",
    )
  }

  private fun generatePeriodicTasks() {
    taskGeneratorJob = CoroutineScope(Dispatchers.Default).launch {
      var period = Random.nextInt(5, 15)
      while (true) {
        val task = generateTask()
        persistTask(task)
        delay(period * 1000L)
        period = Random.nextInt(5, 15)
      }
    }
  }

  fun stopGeneratingTasks() {
    taskGeneratorJob?.cancel()
  }

  fun init() {
    generatePeriodicTasks()
  }

  suspend fun persistTask(task: CreateTaskDto): Task {
    val savedTask = withContext(Dispatchers.IO) {
      mutex.withLock { taskRepository.save(task) }
    }
    return savedTask
  }
}