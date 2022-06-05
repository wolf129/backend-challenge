package com.example.backendchallenge.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepository: JpaRepository<Task, Long> {

  @Query("SELECT t FROM Task t ORDER BY :orderBy ASC")
  suspend fun findAllTasks(orderBy: String): List<Task>

}