package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.GameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class NewGameResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    void testNewGameWithValidPlayer(){
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + NewGameResource.GAME +  NewGameResource.NEW)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testNewGameWithInvalidPlayer() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + NewGameResource.GAME +  NewGameResource.NEW)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}