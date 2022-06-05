package com.example.backendchallenge.database

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.stereotype.Service
import kotlin.random.Random

interface TaskSchedulerService {
  fun stopGeneratingTasks()
  suspend fun persistTask(createTask: CreateTaskDto): Task
}

@Service
class TaskSchedulerServiceImpl(
  private val taskRepository: TaskRepository
) : TaskSchedulerService {

  // Concurrency
  private val mutex = Mutex()
  private var taskGeneratorJob: Job? = null

  init {
    generatePeriodicTasks()
  }

  private fun generateTask(): CreateTaskDto {
    val currentTime = System.currentTimeMillis()
    return CreateTaskDto(
      dueDate = currentTime + Random.nextLong(592_000_000L, 2_592_000_000L),
      title = "Task created by scheduler at: $currentTime",
      priority = Random.nextInt(0, 20),
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

  override fun stopGeneratingTasks() {
    taskGeneratorJob?.cancel()
  }

  override suspend fun persistTask(createTask: CreateTaskDto): Task {
    val savedTask = withContext(Dispatchers.IO) {
      mutex.withLock { taskRepository.save(createTask.toTask()) }
    }
    return savedTask
  }
}