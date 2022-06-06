package com.example.backendchallenge

import com.example.backendchallenge.services.TaskSchedulerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class BackendChallengeApplication {

  @Autowired
  private lateinit var taskSchedulerService: TaskSchedulerService

  @EventListener(ApplicationReadyEvent::class)
  fun runAfterStartup() {
    taskSchedulerService
  }

}

fun main(args: Array<String>) {
  runApplication<BackendChallengeApplication>(*args)
}
