package com.hmo.cyclism;

import com.hmo.cyclism.model.Cyclist;
import com.hmo.cyclism.repository.CyclistRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoutesIntegrationTest {

    @Autowired
    ApplicationContext context;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CyclistRepository repository;

    @BeforeAll
    void clear() {
        this.webTestClient = WebTestClient.bindToApplicationContext(this.context).apply(springSecurity()).configureClient()
                .build();
        repository.deleteAll().subscribe();
    }

    @Test
    @DisplayName("Test get all cyclists")
    public void should_return_status_ok_when_find_all() {
        webTestClient.get().uri("/cyclists")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Test create cyclist")
    public void should_return_cyclist_json_when_create_cyclist() {
        webTestClient.post().uri("/cyclists").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new Cyclist("Mikel Landa Meana", "Movistar")), Cyclist.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Mikel Landa Meana");

    }

    @Test
    @DisplayName("Test update cyclist")
    public void should_return_updated_cyclist_when_update() {

        Mono<Cyclist> cyclist = repository.save(new Cyclist
                ("Richie Porte",
                        "BMC"));

        Cyclist cyclistToUpdate = cyclist.block();
        cyclistToUpdate.setTeam("Trek Segafredo");
        webTestClient.put().uri("/cyclists/" + cyclistToUpdate.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(cyclistToUpdate), Cyclist.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.team").isEqualTo("Trek Segafredo");
    }

    @Test
    @DisplayName("Test delete cyclist")
    public void should_return_no_content_when_delete_cyclist() {
        Mono<Cyclist> cyclist = repository.save(new Cyclist(
                "Vincenzo Nibali",
                "Bahrain Merida"));

        webTestClient.delete().uri("/cyclists/" + cyclist.block().getId())
                .exchange()
                .expectStatus().isNoContent();

    }

}

