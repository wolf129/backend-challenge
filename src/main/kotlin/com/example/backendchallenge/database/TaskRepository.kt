package com.example.backendchallenge.database

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TaskRepository: CoroutineCrudRepository<Task, Long> {

  @Query("SELECT t FROM Task t ORDER BY :orderBy ASC")
  suspend fun findAllTasks(orderBy: String): List<Task>

}