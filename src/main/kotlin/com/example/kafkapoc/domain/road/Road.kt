package com.example.kafkapoc.domain.road

import com.example.kafkapoc.core.messaging.BaseEntityMessage
import com.example.kafkapoc.domain.BaseEntity
import com.example.kafkapoc.domain.location.Location
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class Road(

    val name: String = "",

    @OneToMany(cascade = [CascadeType.ALL])
    val locations: List<Location> = emptyList(),

    ) : BaseEntity(), BaseEntityMessage