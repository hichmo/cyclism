package com.hmo.cyclism.routes.handler;

import com.hmo.cyclism.routes.model.Cyclist;
import com.hmo.cyclism.routes.repository.CyclistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Service
public class CyclistHandler {

    @Autowired
    private CyclistRepository repository;

    public Mono<ServerResponse> handleFindAll(ServerRequest request) {
        return ok().body(repository.findAll(), Cyclist.class);
    }

    public Mono<ServerResponse> handleCreate(ServerRequest request) {
        return status(HttpStatus.CREATED).body(request.bodyToMono(Cyclist.class)
                .flatMap(repository::save), Cyclist.class);
    }

    public Mono<ServerResponse> handleUpdate(ServerRequest request) {
        return ok().body(request.bodyToMono(Cyclist.class)
                .flatMap(cyclist ->
                        repository.findById(request.pathVariable("id"))
                                .flatMap(existingCyclist -> {
                                            existingCyclist.setTeam(cyclist.getTeam());
                                            existingCyclist.setName(cyclist.getName());
                                            return repository.save(existingCyclist);
                                        }
                                )
                ), Cyclist.class).doOnError(e -> Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Could not update a cyclist", e)));
    }

    public Mono<ServerResponse> handleDelete(ServerRequest request) {
        var id = request.pathVariable("id");
        return repository.findById(id)
                .doOnSuccess(repository::delete).then(ServerResponse.noContent().build())
                .onErrorResume(e -> Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cyclist with id " + id + " does not exists", e)));
    }

    public Mono<ServerResponse> handleFindById(ServerRequest request) {
        return ok().body(repository.findById(request.pathVariable("id")), Cyclist.class);
    }
}
