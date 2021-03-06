package com.example.backendchallenge.domain

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "task")
data class Task(
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  val id: UUID? = null,
  @Column(name = "created_at", nullable = false)
  val createdAt: Long,
  @Column(name = "updated_at", nullable = true)
  val updatedAt: Long? = null,
  @Column(name = "due_date", nullable = false)
  val dueDate: Long,
  @Column(name = "resolved_at", nullable = true)
  val resolvedAt: Long? = null,
  @Column(name = "title", nullable = false)
  val title: String,
  @Column(name = "description", nullable = true)
  val description: String? = null,
  @Column(name = "priority", nullable = false)
  val priority: Int,
  @Column(name = "status", nullable = false)
  val status: String,
)