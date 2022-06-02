package com.example.backendchallenge.database

import javax.persistence.*

@Entity
@Table(name = "task")
data class Task(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(name = "created_at", nullable = true)
  val createdAt: Long,
  @Column(name = "updated_at", nullable = true)
  val updatedAt: Long,
  @Column(name = "due_data", nullable = true)
  val dueDate: Long,
  @Column(name = "resolved_at", nullable = true)
  val resolvedAt: Long,
  @Column(nullable = true)
  val title: String,
  @Column(nullable = true)
  val description: String,
  @Column(nullable = true)
  val priority: Int,
  @Column(nullable = true)
  val status: String,
)