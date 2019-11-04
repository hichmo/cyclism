package com.hmo.cyclism.routes

import com.hmo.cyclism.handler.CyclistHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(@Autowired val handler: CyclistHandler) {

    @Bean
    fun cyclistRouter() = router {
        "/cyclists".nest {
            GET("", accept(TEXT_EVENT_STREAM), handler::handleFindAll)
            GET("{id}", handler::handleFindById)
        }
        POST("/cyclists", handler::handleCreate)
        PUT("/cyclists/{id}", handler::handleUpdate)
        DELETE("/cyclists/{id}", handler::handleDelete)
    }

}
