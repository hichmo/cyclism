package com.hmo.cyclism.routes.routes;

import com.hmo.cyclism.routes.handler.CyclistHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {

    @Autowired
    private CyclistHandler cyclistHandler;

    @Bean
    public RouterFunction<ServerResponse> routerPublish() {
        return route(GET("/cyclists"), cyclistHandler::handleFindAll)
                .andRoute(GET("/cyclists/{id}"), cyclistHandler::handleFindById)
                .andRoute(POST("/cyclists"), cyclistHandler::handleCreate)
                .andRoute(PUT("/cyclists/{id}"), cyclistHandler::handleUpdate)
                .andRoute(DELETE("/cyclists/{id}"), cyclistHandler::handleDelete);
    }
}
