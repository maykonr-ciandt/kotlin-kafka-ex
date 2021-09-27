package com.example.kafkapoc.domain.location

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "location", path = "locations")
interface LocationRepository : PagingAndSortingRepository<Location, Long>