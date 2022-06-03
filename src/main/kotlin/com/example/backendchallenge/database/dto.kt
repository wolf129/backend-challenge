package com.example.backendchallenge.database

data class TaskDto(
  val id: Long?,
  val createdAt: Long,
  val updatedAt: Long,
  val dueDate: Long,
  val resolvedAt: Long?,
  val title: String,
  val description: String?,
  val priority: Int,
  val status: String,
)