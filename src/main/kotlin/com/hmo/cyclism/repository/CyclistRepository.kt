package com.hmo.cyclism.repository

import com.hmo.cyclism.model.Cyclist
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CyclistRepository : ReactiveMongoRepository<Cyclist, String> {

    fun findByName(name: String): Mono<Cyclist>

}