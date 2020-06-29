package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_services.JwtService;
import es.upm.miw.klondike.dtos.TokenDto;
import es.upm.miw.klondike.exceptions.JwtException;
import es.upm.miw.klondike.models.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class RestService {

    @Autowired
    private JwtService jwtService;

    @Value("${server.servlet.contextPath}")
    private String contextPath;

    @Value("${klondike.player.user}")
    private String playerUser;

    @Value("${klondike.player.password}")
    private String playerPassword;

    private TokenDto tokenDto;

    private boolean isRole(Role role) {
        try {
            return this.tokenDto != null && this.jwtService.roles("Bearer "
                    + tokenDto.getToken()).contains(role.name());
        } catch (JwtException e) {
            LogManager.getLogger(this.getClass()).error("------- is role -----------");
        }
        return false;
    }

    private WebTestClient login(Role role, String login, String pass, WebTestClient webTestClient) {
        if (!this.isRole(role)) {
            return login(login, pass, webTestClient);
        } else {
            return webTestClient.mutate()
                    .defaultHeader("Authorization", "Bearer " + this.tokenDto.getToken()).build();
        }
    }

    public WebTestClient loginPlayer(WebTestClient webTestClient) {
        return this.login(Role.PLAYER, this.playerUser, this.playerPassword, webTestClient);
    }


    public WebTestClient login(String user, String pass, WebTestClient webTestClient) {
        this.tokenDto = webTestClient
                .mutate().filter(basicAuthentication(user, pass)).build()
                .post().uri(contextPath + LoginResource.AUTHENTICATION + LoginResource.LOGIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .returnResult().getResponseBody();
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.tokenDto.getToken()).build();
    }

    public TokenDto getTokenDto() {
        return tokenDto;
    }

    public String getPlayerUser() {
        return playerUser;
    }

}