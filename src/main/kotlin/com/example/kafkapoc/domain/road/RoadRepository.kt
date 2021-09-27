package com.example.kafkapoc.domain.road

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "location", path = "locations")
interface RoadRepository : PagingAndSortingRepository<Road, Long>