package com.example.backendchallenge.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TaskRepository: JpaRepository<Task, UUID> {

  @Query("SELECT t FROM Task t ORDER BY :orderBy ASC")
  fun findAllTasks(orderBy: String): List<Task>

  fun findByIdOrNull(id: UUID): Task? = findById(id).orElse(null)

}