package com.hmo.cyclism.routes;

import com.hmo.cyclism.handler.CyclistHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {

    @Autowired
    private CyclistHandler cyclistHandler;


    @Bean
    public RouterFunction<ServerResponse> routerCyclist() {
        return route()
                .path("/cyclists", builder -> builder
                        .GET("/{id}", accept(APPLICATION_JSON), cyclistHandler::handleFindById)
                        .GET("", accept(APPLICATION_JSON), cyclistHandler::handleFindAll)
                        .POST("/cyclists", cyclistHandler::handleCreate)
                        .PUT("/cyclists/{id}", cyclistHandler::handleUpdate)
                        .DELETE("/cyclists/{id}", cyclistHandler::handleDelete)).build();

    }
}
