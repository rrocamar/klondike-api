package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.daos.GameDao;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameMemento;
import es.upm.miw.klondike.models.SavedGame;
import es.upm.miw.klondike.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OpenGameController extends GameController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private GameDao gameDao;

    public List<GameMinimunDto> getAllGames(String login) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new NotFoundException("User:" + login));
        return this.gameDao.findSavedGameByUser(user);
    }

    public void openGame(String login, Long id) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new NotFoundException("User:" + login));
        SavedGame savedGame = gameDao.findByIdAndUser(id, user).orElseThrow(
                () -> new NotFoundException("Game:" + id + " for user: " + login));
        Game game = new Game();
        game.restoreMemento((GameMemento)savedGame.getGameMemento());
        super.createNewGame(login, game);
    }
}
