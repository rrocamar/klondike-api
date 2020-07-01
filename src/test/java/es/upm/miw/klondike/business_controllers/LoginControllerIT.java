package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.TokenDto;
import es.upm.miw.klondike.dtos.UserDto;
import es.upm.miw.klondike.dtos.UserMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class LoginControllerIT {

    @Autowired
    private LoginController loginController;

    private User test;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void loadUser() {
        this.test = this.userDao.findByLogin("test").orElse(null);
    }

    @Test
    void testLoginValidUser(){
        TokenDto tokenDto = this.loginController.login(this.test.getLogin());
        assertNotNull(tokenDto);
        assertNotNull(tokenDto.getToken());
    }

    @Test
    void testLoginInvalidUser(){
        String userNotExists = "userNotExists!#&%%%423234dfsr,._¨67·$34";
        assertThrows(NotFoundException.class, () -> this.loginController.login(userNotExists));
    }

    @Test
    void testLoginAvailable(){
        String userNotExists = "userNotExists!#&%%%423234dfsr,._¨67·$34";
        UserMinimunDto userMinimunDto = this.loginController.isAvailable(userNotExists);
        assertTrue(userMinimunDto.isAvailable());
        assertEquals(userNotExists, userMinimunDto.getLogin());
        userMinimunDto = this.loginController.isAvailable(this.test.getLogin());
        assertFalse(userMinimunDto.isAvailable());
        assertEquals(this.test.getLogin(), userMinimunDto.getLogin());
    }

    @Test
    void testCreateUser() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().name("User for test").surname("Surname for test")
                .email("emailUserTest@klondike.es").dni("12345678T").login("userForLoginTest")
                .active(true).roles(new Role[]{Role.PLAYER}).password("testPassword")
                .registrationDate(now).build();
        UserDto newUser = this.loginController.create(new UserDto(user));
        assertEquals(user.getLogin(), newUser.getLogin());
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getSurname(), newUser.getSurname());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getDni(), newUser.getDni());
        assertEquals(user.isActive(), newUser.isActive());
        assertEquals(user.getRegistrationDate(), newUser.getRegistrationDate());
        user = this.userDao.findByLogin(user.getLogin()).orElse(null);
        this.userDao.delete(user);
        assertThrows(NotFoundException.class, () -> this.loginController.login("userForLoginTest"));
    }
}