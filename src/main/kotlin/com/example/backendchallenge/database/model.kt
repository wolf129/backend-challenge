package com.example.backendchallenge.database

import javax.persistence.*

@Entity
@Table(name = "task")
data class Task(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(name = "created_at", nullable = false)
  val createdAt: Long,
  @Column(name = "updated_at", nullable = false)
  val updatedAt: Long,
  @Column(name = "due_data", nullable = false)
  val dueDate: Long,
  @Column(name = "resolved_at", nullable = true)
  val resolvedAt: Long? = null,
  @Column(nullable = false)
  val title: String,
  @Column(nullable = false)
  val description: String,
  @Column(nullable = false)
  val priority: Int,
  @Column(nullable = false)
  val status: String,
)