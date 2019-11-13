package com.hmo.cyclism.handler

import com.hmo.cyclism.model.Cyclist
import com.hmo.cyclism.repository.CyclistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class CyclistHandler(
        @Autowired
        private val repository: CyclistRepository
) {
    fun handleFindAll(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
            .body(repository.findAll(), Cyclist::class.java)

    fun handleFindById(request: ServerRequest): Mono<ServerResponse> {
        return repository.findById(request.pathVariable("id"))
                .flatMap { cy -> ServerResponse.ok().body(cy, Cyclist::class.java) }
                .switchIfEmpty(ServerResponse.notFound().build())

    }

    fun handleCreate(request: ServerRequest): Mono<ServerResponse> {
        return ok().body(request.bodyToMono(Cyclist::class.java)
                .flatMap { cyclistToUpdate ->
                    repository.save(cyclistToUpdate)
                }, Cyclist::class.java)
    }

    fun handleUpdate(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Cyclist::class.java)
                .flatMap { cyclistToUpdate ->
                    repository.findById(request.pathVariable("id"))
                            .flatMap { existingCyclist ->
                                existingCyclist.team = cyclistToUpdate.team
                                existingCyclist.name = cyclistToUpdate.name
                                existingCyclist.id = cyclistToUpdate.id
                                ok().body(repository.save(existingCyclist), Cyclist::class.java)
                            }
                            .switchIfEmpty(ServerResponse.badRequest().build())
                }
    }

    fun handleDelete(request: ServerRequest): Mono<ServerResponse> {
        return repository.findById(request.pathVariable("id"))
                .flatMap { existingCyclist ->
                    repository.delete(existingCyclist)
                            .then(ServerResponse.noContent().build())
                }
                .switchIfEmpty(ServerResponse.notFound().build())
    }
}
