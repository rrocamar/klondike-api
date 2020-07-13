package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class OpenGameControllerIT {

    @Autowired
    private QuitGameController quitGameController;

    @Autowired
    private OpenGameController openGameController;

    private User user, test;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void loadUser() {
        this.test = userDao.findByLogin("test").orElse(null);
        LocalDateTime now = LocalDateTime.now();
        this.user = User.builder().name("User for test").surname("Surname for test")
                .email("emailUserTest@klondike.es").dni("12345678T").login("userForTest")
                .active(true).roles(new Role[]{Role.PLAYER}).password("testPassword")
                .registrationDate(now).build();
    }

    @Test
    void testOpenGame(){
        long idGame = 25;
        this.quitGameController.quitGame(this.test.getLogin());
        assertThrows(NotFoundException.class, () -> this.openGameController.getGame(test.getLogin()));
        this.openGameController.openGame(test.getLogin(), idGame);
        Game game = this.openGameController.getGame(test.getLogin());
        assertNotNull(game);
        this.quitGameController.quitGame(this.test.getLogin());
        assertThrows(NotFoundException.class, () -> this.openGameController.getGame(user.getLogin()));
        assertThrows(NotFoundException.class, () -> this.openGameController.openGame(user.getLogin(), idGame));
    }

    @Test
    void testGetAllGames(){
        List<GameMinimunDto> games = this.openGameController.getAllGames(test.getLogin());
        assertNotNull(games);
        assertTrue(games.size()>0);
        assertThrows(NotFoundException.class, () -> this.openGameController.getAllGames(user.getLogin()));
    }
}