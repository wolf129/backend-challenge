package com.example.backendchallenge.database

import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository: JpaRepository<Task, Long>