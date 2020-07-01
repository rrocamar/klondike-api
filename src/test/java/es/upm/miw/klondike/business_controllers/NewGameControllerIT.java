package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.Role;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class NewGameControllerIT {

    @Autowired
    private QuitGameController quitGameController;

    @Autowired
    private NewGameController newGameController;

    private User test;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void loadUser() {
        this.test = userDao.findByLogin("test").orElse(null);
    }

    @Test
    void testCreateNewGame(){
        this.quitGameController.quitGame(this.test.getLogin());
        assertThrows(NotFoundException.class, () -> this.newGameController.getGame(test.getLogin()));
        this.newGameController.createNewGame(test.getLogin());
        Game game = this.newGameController.getGame(test.getLogin());
        assertNotNull(game);
    }
}