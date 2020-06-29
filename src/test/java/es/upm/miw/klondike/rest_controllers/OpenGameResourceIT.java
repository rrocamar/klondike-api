package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.GameMinimunDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class OpenGameResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    void testGetAllNamesOfGamesWithValidPlayer(){
        List<GameMinimunDto> allGames = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.NAMES_OF_GAMES)
                .exchange()
                .expectStatus().isOk().expectBody(List.class)
                .returnResult().getResponseBody();
        assertNotNull(allGames);
        assertTrue(allGames.size()>0);
    }

    @Test
    void testGetAllNamesOfGamesWithInvalidPlayer() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .get().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.NAMES_OF_GAMES)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testOpenGameWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testOpenGameWithInvalidPlayer() {
        int idGame = 17;
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isUnauthorized();
    }

}