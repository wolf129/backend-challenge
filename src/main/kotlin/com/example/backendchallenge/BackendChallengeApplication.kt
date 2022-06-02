package com.example.backendchallenge

import com.example.backendchallenge.database.Task
import com.example.backendchallenge.database.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import kotlin.random.Random

@SpringBootApplication
class BackendChallengeApplication {
  private val logger = LoggerFactory.getLogger(BackendChallengeApplication::class.java)

  @Autowired
  private lateinit var repository: TaskRepository

  @EventListener(ApplicationReadyEvent::class)
  fun runAfterStartup() {
    var tasks: List<Task> = repository.findAll()
    logger.debug(tasks.toString())

    val task = Task(
      title = "test_task",
      description = "simple stuff",
      createdAt = System.currentTimeMillis(),
      updatedAt = System.currentTimeMillis(),
      dueDate = System.currentTimeMillis(),
      resolvedAt = System.currentTimeMillis(),
      priority = Random.nextInt(),
      status = "unresolved",
    )
    repository.save(task)
    logger.debug(task.toString())

    tasks = repository.findAll()
    logger.debug(tasks.toString())
  }
}

fun main(args: Array<String>) {
  runApplication<BackendChallengeApplication>(*args)
}
