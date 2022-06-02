package com.example.backendchallenge.database

import java.time.LocalDateTime

data class TaskDto(
  val id: Long,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime,
  val dueDate: LocalDateTime,
  val resolvedAt: LocalDateTime,
  val title: String,
  val description: String,
  val priority: Int,
  val status: String,
)