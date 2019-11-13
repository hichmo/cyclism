package com.hmo.cyclism

import com.hmo.cyclism.model.Cyclist
import com.hmo.cyclism.repository.CyclistRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutesIntegrationTest(
        @Autowired val webTestClient: WebTestClient,
        @Autowired val repository: CyclistRepository) {

    @BeforeAll
    fun setup() {
        repository.deleteAll().subscribe()
    }

    @Test
    @DisplayName("Test find all cyclists status OK")
    fun should_return_status_ok_when_find_all() {
        webTestClient.get().uri("/cyclists")
                .exchange()
                .expectStatus().isOk
    }

    @Test
    @DisplayName("Test find cyclist by wrong ID status not found")
    fun should_return_status_not_found_when_find_by_unknown_id() {
        webTestClient.get().uri("/cyclists/unknown_id")
                .exchange()
                .expectStatus().isNotFound
                .expectBody().isEmpty
    }

    @Test
    @DisplayName("Test create cyclist status OK and id not empty")
    fun should_return_cyclist_when_create() {
        webTestClient.post().uri("/cyclists")
                .body(Mono.just(Cyclist(null, "Mikel Landa Meana", "Movistar")),
                        Cyclist::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isNotEmpty

    }

    @Test
    @DisplayName("Test delete cyclist status no content")
    fun should_return_no_content_when_delete_cyclist() {
        createCyclist().subscribe()
        val id = findOneCyclist().block()!!.id
        webTestClient.delete().uri("/cyclists/$id")
                .exchange()
                .expectStatus().isNoContent
    }

    @Test
    @DisplayName("Test delete cyclist wrong id")
    fun should_return_error_when_delete_cyclist_with_wrong_id() {
        webTestClient.delete().uri("/cyclists/wrong_id")
                .exchange()
                .expectStatus().isNotFound
    }

    @Test
    @DisplayName("Test update cyclist return new team")
    fun should_return_new_team_when_update_cyclist() {
        createCyclist().subscribe()
        val cyclist = findOneCyclist().block()
        cyclist!!.team = "Trek Segafredo"

        webTestClient.put()
                .uri("/cyclists/" + cyclist.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(cyclist), Cyclist::class.java)
                .exchange()
                .expectBody()
                .jsonPath("$.team").isEqualTo("Trek Segafredo")

    }

    fun createCyclist(): Mono<Cyclist> {
        return repository.save(Cyclist("",
                "Vincenzo Nibali",
                "Bahrain Merida"))
    }

    fun findOneCyclist(): Mono<Cyclist> = repository.findByName("Vincenzo Nibali")
}

