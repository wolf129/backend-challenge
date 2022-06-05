package com.example.backendchallenge.database

fun Task.toTaskDto(): TaskDto {
  return TaskDto(id!!, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
}

fun TaskDto.toTask(): Task {
  return Task(id, createdAt, updatedAt, dueDate, resolvedAt, title, description, priority, status)
}

fun CreateTaskDto.toTask(): Task {
  val time = System.currentTimeMillis()
  return Task(
    createdAt = time,
    dueDate = dueDate,
    title = title,
    description = description,
    priority = priority,
    status = "created"
  )
}