package es.upm.miw.klondike.rest_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;
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
/*
    @Test
    void testReadAdminWithAdminRole() {
        this.restService.loginAdmin(this.webTestClient)
                .get().uri(contextPath + USERS + UserResource.MOBILE_ID, this.restService.getAdminMobile())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(Assertions::assertNotNull)
                .value(user -> assertEquals(this.restService.getAdminMobile(), user.getMobile()));
    }

    @Test
    void testReadOperatorWithManagerRole() {
        this.restService.loginManager(this.webTestClient)
                .get().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666002")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(Assertions::assertNotNull)
                .value(user -> assertEquals("666666002", user.getMobile()));

    }

    @Test
    void testReadCustomerWithRoleOperator() {
        this.restService.loginManager(this.webTestClient)
                .get().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666004")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(Assertions::assertNotNull)
                .value(user -> assertEquals("666666004", user.getMobile()));
    }

    @Test
    void testReadOperatorWithRoleOperator() {
        this.restService.loginOperator(this.webTestClient)
                .get().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666003")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void testReadAll() {
        this.restService.loginAdmin(this.webTestClient)
                .get().uri(contextPath + USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserMinimumDto.class)
                .value(Assertions::assertNotNull)
                .value(list -> assertTrue(list.size() > 1));
    }

    @Test
    void testCreateUserAlreadyExists() {
        this.restService.loginAdmin(this.webTestClient)
                .post().uri(contextPath + USERS)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("666666000").username("all-roles").build())))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testCreateUserBadNumber() {
        this.restService.loginAdmin(this.webTestClient)
                .post().uri(contextPath + USERS)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("7").username("m001").build())))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testCreateUser() {
        this.restService.loginAdmin(this.webTestClient)
                .post().uri(contextPath + USERS)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("616117255").username("m001").dni("51714988V").address("C/M, 14").email("m001@gmail.com").build()))
                ).exchange().expectStatus().isOk().expectBody(UserDto.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void testUpdateUserNonExist() {
        this.restService.loginAdmin(this.webTestClient)
                .put().uri(contextPath + USERS + UserResource.MOBILE_ID, "999999999")
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("666666007").build())
                )).exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateUserBadNumber() {
        this.restService.loginAdmin(this.webTestClient)
                .put().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666002")
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("7").build())
                )).exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateUserToMobileAlreadyExists() {
        this.restService.loginAdmin(this.webTestClient)
                .put().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666002")
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("666666003").build())
                )).exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testUpdateUser() {
        this.restService.loginAdmin(this.webTestClient)
                .put().uri(contextPath + USERS + UserResource.MOBILE_ID, "666666006")
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("666666006").username("u006").password("p006").dni("66666606W").address("C/TPV, 6").email("u006@gmail.com").build())
                )).exchange().expectStatus().isOk().expectBody(UserDto.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void updatePasswordMobileBadRequest() {
        this.restService.loginAdmin(this.webTestClient)
                .patch().uri(contextPath + USERS + "/password" + UserResource.MOBILE_ID, "")
                .body(BodyInserters.fromObject(
                        new UserCredentialDto("6", "5")
                )).exchange().expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePasswordMobileNotFound() {
        this.restService.loginAdmin(this.webTestClient)
                .patch().uri(contextPath + USERS + "/password" + UserResource.MOBILE_ID, "asda")
                .body(BodyInserters.fromObject(
                        new UserCredentialDto("6", "5")
                )).exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateRoles() {
        Role[] rolCustomer = new Role[]{Role.CUSTOMER};
        Role[] rolOperatorAndAdmin = new Role[]{Role.OPERATOR, Role.ADMIN};
        UserDto userDto = this.restService.loginAdmin(this.webTestClient)
                .post().uri(contextPath + USERS)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("687144426").username("m001").dni("51714988V").address("C/M, 14").email("m001@gmail.com").roles(rolCustomer).build()))
                ).exchange().expectStatus().isOk().expectBody(UserDto.class)
                .returnResult().getResponseBody();

        this.restService.loginAdmin(this.webTestClient)
                .patch().uri(contextPath + USERS + UserResource.MOBILE_ID, "687144426")
                .body(BodyInserters.fromObject(
                        new UserMinimumDto("687144426", "m001", rolOperatorAndAdmin)
                )).exchange().expectStatus().isOk().expectBody(UserDto.class)
                .value(Assertions::assertNotNull)
                .value(user -> assertTrue(user.getRoles().length > 1))
                .value(user -> assertArrayEquals(user.getRoles(), rolOperatorAndAdmin));

    }

    @Test
    void testUpdateRolesNotFound() {
        Role[] rolCustomer = new Role[]{Role.CUSTOMER};
        this.restService.loginAdmin(this.webTestClient)
                .patch().uri(contextPath + USERS + UserResource.MOBILE_ID, "a")
                .body(BodyInserters.fromObject(
                        new UserMinimumDto("687144426", "m001", rolCustomer)
                )).exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void testSearchUser() {
        List<UserDto> users = this.restService.loginAdmin(webTestClient)
                .get().uri(uriBuilder -> uriBuilder
                        .path(contextPath + USERS + SEARCH)
                        .queryParam("mobile", "6")
                        .queryParam("username", "")
                        .queryParam("dni", "")
                        .queryParam("address", "")
                        .build()).exchange().expectStatus().isOk().expectBodyList(UserDto.class)
                .returnResult().getResponseBody();
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    void testSearchUserSomeResults() {
        this.restService.loginAdmin(this.webTestClient)
                .post().uri(contextPath + USERS)
                .body(BodyInserters.fromObject(
                        new UserDto(User.builder().mobile("985632145").username("manager").dni("51714988V").address("C/M, 14").email("m001@gmail.com").build()))
                ).exchange().expectStatus().isOk().expectBody(UserDto.class)
                .value(Assertions::assertNotNull);
        List<UserDto> users = this.restService.loginAdmin(webTestClient)
                .get().uri(uriBuilder -> uriBuilder
                        .path(contextPath + USERS + SEARCH)
                        .queryParam("mobile", "6")
                        .queryParam("username", "manager")
                        .queryParam("dni", "")
                        .queryParam("address", "")
                        .build()).exchange().expectStatus().isOk().expectBodyList(UserDto.class)
                .returnResult().getResponseBody();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    void testSearchUserNotFound() {
        this.restService.loginAdmin(webTestClient)
                .get().uri(uriBuilder -> uriBuilder
                .path(contextPath + USERS + SEARCH)
                .queryParam("mobile", "zz")
                .queryParam("username", "")
                .queryParam("dni", "")
                .queryParam("address", "")
                .build()).exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }
*/
}