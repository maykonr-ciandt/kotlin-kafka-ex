package com.example.kafkapoc.domain

import com.example.kafkapoc.core.messaging.BaseEntityMessageListener
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(BaseEntityMessageListener::class)
abstract class BaseEntity : Serializable {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private val id: UUID = UUID.randomUUID()

    @Column(updatable = false)
    private var createdAt: OffsetDateTime? = null

    private var updatedAt: OffsetDateTime? = null

    @PrePersist
    private fun prePersist() {
        createdAt = OffsetDateTime.now()
        updatedAt = OffsetDateTime.now()
    }

    @PreUpdate
    protected fun preUpdate() {
        updatedAt = OffsetDateTime.now()
    }
}