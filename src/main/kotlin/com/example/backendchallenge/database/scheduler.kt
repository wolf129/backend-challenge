package com.example.backendchallenge.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TaskScheduler {

  private val mutex = Mutex()
  @Autowired
  private lateinit var taskRepository: TaskRepository

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

  private suspend fun generatePeriodicTasks() {
    CoroutineScope(Dispatchers.IO).launch {
      var period = Random.nextInt(5, 15)
      while(true) {
        val task = generateTask()
        persistTask(task)
        delay(period * 1000L)
        period = Random.nextInt(5, 15)
      }
    }
  }

  suspend fun init() {
    generatePeriodicTasks()
  }

  suspend fun persistTask(task: Task): Task {
    val savedTask = mutex.withLock {
      taskRepository.save(task)
    }
    return savedTask
  }
}