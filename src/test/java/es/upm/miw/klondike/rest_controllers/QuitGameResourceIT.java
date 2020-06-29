package es.upm.miw.klondike.rest_controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class QuitGameResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    void testQuitGamesWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + QuitGameResource.GAME +  QuitGameResource.QUIT)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testQuitGamesWithInvalidPlayer(){
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + QuitGameResource.GAME +  QuitGameResource.QUIT)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}