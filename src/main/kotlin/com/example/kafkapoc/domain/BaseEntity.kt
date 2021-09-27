package com.example.kafkapoc.domain

import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(BaseEntityListener::class)
abstract class BaseEntity : Serializable {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    open val id: UUID = UUID.randomUUID()

    @Column(updatable = false)
    open var createdAt: OffsetDateTime? = null

    open var updatedAt: OffsetDateTime? = null

    @PrePersist
    private fun prePersist() {
        createdAt = createdAt ?: OffsetDateTime.now()
    }

    @PreUpdate
    protected fun preUpdate() {
        updatedAt = OffsetDateTime.now()
    }
}