package com.example.backendchallenge.database

import java.util.UUID

data class TaskDto(
  val id: UUID,
  val createdAt: Long,
  val updatedAt: Long? = null,
  val dueDate: Long,
  val resolvedAt: Long? = null,
  val title: String,
  val description: String? = null,
  val priority: Int,
  val status: String,
)

data class UpdateTaskDto(
  val id: UUID,
  val dueDate: Long,
  val resolvedAt: Long? = null,
  val title: String,
  val description: String? = null,
  val priority: Int,
  val status: String,
)

data class CreateTaskDto(
  val title: String,
  val description: String? = null,
  val dueDate: Long,
  val priority: Int,
)