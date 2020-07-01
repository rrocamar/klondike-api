package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.GameDao;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.SavedGame;
import es.upm.miw.klondike.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class SaveGameControllerIT {

    @Autowired
    private QuitGameController quitGameController;

    @Autowired
    private NewGameController newGameController;

    @Autowired
    private SaveGameController saveGameController;

    private User test;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GameDao gameDao;

    @BeforeEach
    void loadUser() {
        this.test = userDao.findByLogin("test").orElse(null);
    }

    @Test
    void testSaveGame(){
        GameMinimunDto game = new GameMinimunDto();
        game.setName("A name for my test game");
        this.quitGameController.quitGame(this.test.getLogin());
        assertThrows(NotFoundException.class,() -> this.saveGameController.save(this.test.getLogin(),game));
        this.newGameController.createNewGame(this.test.getLogin());
        this.saveGameController.save(this.test.getLogin(),game);
        SavedGame savedGame = this.gameDao.findByNameAndUser(game.getName(),this.test).orElse(null);
        assertNotNull(savedGame);
        assertEquals(game.getName(), savedGame.getName());
        this.gameDao.delete(savedGame);
    }
}