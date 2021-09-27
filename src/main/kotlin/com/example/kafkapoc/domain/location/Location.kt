package com.example.kafkapoc.domain.location

import com.example.kafkapoc.core.messaging.BaseEntityMessage
import com.example.kafkapoc.domain.BaseEntity
import javax.persistence.Entity

@Entity
data class Location(
    val name: String = "",
) : BaseEntity(), BaseEntityMessage