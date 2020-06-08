package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.daos.GameDao;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.SavedGame;
import es.upm.miw.klondike.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class SaveGameController extends GameController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private GameDao gameDao;

    public void save(String login, GameMinimunDto gameMinimunDto){
        Game game = getGame(login);
        User user = userDao.findByLogin(login).orElseThrow(() -> new NotFoundException("User:" + login));
        SavedGame savedGame;
        Optional<SavedGame> optionalGame = gameDao.findByNameAndUser(gameMinimunDto.getName(), user);
        if(optionalGame.isPresent())
            savedGame = optionalGame.get();
        else
            savedGame = new SavedGame();
        savedGame.setGameMemento(game.createMemento());
        savedGame.setName(gameMinimunDto.getName());
        savedGame.setUser(user);
        gameDao.save(savedGame);
    }
}
