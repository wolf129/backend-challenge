package com.example.backendchallenge.io

import com.example.backendchallenge.database.TaskRepository
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController {
  @Autowired
  private lateinit var taskRepository: TaskRepository

  @GetMapping("/fetch-all-tasks/{orderBy}")
  fun listAllTasks(
    @PathVariable orderBy: String = "createdAt"
  ): String {
    val tasks = taskRepository.findAll(Sort.by(orderBy))
    return Gson().toJson(tasks.toTypedArray())
  }
}