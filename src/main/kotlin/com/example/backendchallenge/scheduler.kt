package com.example.backendchallenge

import com.example.backendchallenge.database.Task
import kotlin.random.Random

object TaskScheduler {

  fun generateTask(): Task {
    val currentTime = System.currentTimeMillis()
    return Task(
      createdAt = currentTime,
      updatedAt = currentTime,
      dueDate = currentTime + Random.nextLong(592_000_000L,2_592_000_000L),
      title = "task $currentTime",
      description = "description of task $currentTime",
      priority = 0,
      status = "created",
    )
  }


}