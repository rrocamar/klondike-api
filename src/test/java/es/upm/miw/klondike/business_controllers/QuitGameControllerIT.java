package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class QuitGameControllerIT {

    @Autowired
    private NewGameController newGameController;

    @Autowired
    private QuitGameController quitGameController;

    private User test;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void loadUser() {
        this.test = userDao.findByLogin("test").orElse(null);
    }

    @Test
    void testQutGame(){
        this.quitGameController.removeGame(test.getLogin());
        this.newGameController.createNewGame(test.getLogin());
        Game game = this.newGameController.getGame(test.getLogin());
        assertNotNull(game);
        this.quitGameController.removeGame(test.getLogin());
        assertThrows(NotFoundException.class, () -> this.quitGameController.getGame(test.getLogin()));
    }
}