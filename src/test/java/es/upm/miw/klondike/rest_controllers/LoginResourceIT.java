package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.UserDto;
import es.upm.miw.klondike.dtos.UserMinimunDto;
import es.upm.miw.klondike.models.User;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class LoginResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserDao userDao;

    @Test
    void testLogin() {
        this.restService.loginPlayer(this.webTestClient);
        assertTrue(this.restService.getTokenDto().getToken().length() > 10);
    }

    @Test
    void testLoginPlayerPassNull() {
        webTestClient
                .mutate().filter(basicAuthentication(restService.getPlayerUser(), "kk")).build()
                .post().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.LOGIN)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testLoginInvalid() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.LOGIN)
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    void testCreateUserAlreadyExists() {
        webTestClient
                .mutate().build()
                .post().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.REGISTER_USER)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().login("test").password("test").name("test").surname("test").dni("12345678T").email("test@klondike.es").build()))
                )
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testCreateUserBadEmail() {
        webTestClient
                .mutate().build()
                .post().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.REGISTER_USER)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().login("test2").password("test2").name("test2").surname("test2").dni("87654321T").email("test2klondike.es").build()))
                )
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAvailableLogin() {
        String availableLogin = "test2";
        webTestClient
                .mutate().build()
                .get().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.LOGIN_AVAILABLE, availableLogin)
                .exchange().expectBody(UserMinimunDto.class)
                .value(Assertions::assertNotNull).value(result ->
                    {
                        assertEquals(true, result.isAvailable());
                        assertEquals(availableLogin, result.getLogin());
                    }
                );

    }

    @Test
    void testUnavailableLogin() {
        String availableLogin = "test";
        webTestClient
                .mutate().build()
                .get().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.LOGIN_AVAILABLE, availableLogin)
                .exchange().expectBody(UserMinimunDto.class)
                .value(Assertions::assertNotNull).value(result ->
                {
                    assertEquals(false, result.isAvailable());
                    assertEquals(availableLogin, result.getLogin());
                }
        );

    }
}