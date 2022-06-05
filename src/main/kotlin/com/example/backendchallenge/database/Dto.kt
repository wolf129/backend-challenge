package com.example.backendchallenge.database

data class TaskDto(
  val id: Long,
  val createdAt: Long,
  val updatedAt: Long,
  val dueDate: Long,
  val resolvedAt: Long?,
  val title: String,
  val description: String?,
  val priority: Int,
  val status: String,
)

data class UpdateTaskDto(
  val id: Long,
  val dueDate: Long,
  val resolvedAt: Long?,
  val title: String,
  val description: String?,
  val priority: Int,
  val status: String,
)

data class CreateTaskDto(
  val title: String,
  val description: String?,
  val dueDate: Long,
  val priority: Int,
)