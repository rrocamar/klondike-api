package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.daos.GameDao;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.models.SavedGame;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class SaveGameResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private UserDao userDao;

    @Test
    void testSaveGamesWithValidPlayer(){
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + NewGameResource.GAME +  NewGameResource.NEW)
                .exchange()
                .expectStatus().isOk();
        LocalDateTime now = LocalDateTime.now();
        GameMinimunDto gameMinimunDto = new GameMinimunDto();
        gameMinimunDto.setName("test saved game " + now.atZone(ZoneId.of("Europe/Madrid")).toInstant().toEpochMilli());
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + SaveGameResource.GAME +  SaveGameResource.SAVE)
                .body(BodyInserters.fromObject(gameMinimunDto))
                .exchange()
                .expectStatus().isOk();
        User user = userDao.findByLogin(this.restService.getPlayerUser()).orElse(null);
        assertNotNull(user);
        SavedGame savedGame = gameDao.findByNameAndUser(gameMinimunDto.getName(),user).orElse(null);
        assertNotNull(savedGame);
        assertEquals(gameMinimunDto.getName(), savedGame.getName());
        gameDao.delete(savedGame);
    }

    @Test
    void testSaveGamesWithInvalidPlayer(){
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + SaveGameResource.GAME +  SaveGameResource.SAVE)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}