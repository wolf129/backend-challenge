package com.example.backendchallenge

import com.example.backendchallenge.database.TaskScheduler
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class BackendChallengeApplication {
//  private val logger = LoggerFactory.getLogger(BackendChallengeApplication::class.java)

  @Autowired
  private lateinit var taskScheduler: TaskScheduler

  @EventListener(ApplicationReadyEvent::class)
  fun runAfterStartup() {
    runBlocking { taskScheduler.init() }
  }

//  @Bean
//  fun modelMapper() = ModelMapper()
}

fun main(args: Array<String>) {
  runApplication<BackendChallengeApplication>(*args)
}
