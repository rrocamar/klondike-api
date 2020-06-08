package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public abstract class GameController {

    private static HashMap<String,Game> playerGame = new HashMap<>();
    private static HashMap<String, GameCaretaker> playerGameCaretaker = new HashMap<>();

    protected Game getGame(String login){
        Game game = playerGame.get(login);
        if(game == null) {
            throw new NotFoundException("Game for user: " + login);
        }
        return game;
    }

    protected GameCaretaker getGameCaretaker(String login){
        GameCaretaker caretaker = playerGameCaretaker.get(login);
        if(caretaker == null) {
            throw new NotFoundException("Game for user: " + login);
        }
        return caretaker;
    }

    protected void createNewGame(String login, Game game){
        GameCaretaker gameCaretaker = new GameCaretaker();
        gameCaretaker.addMemento(game.createMemento());
        playerGame.put(login, game);
        playerGameCaretaker.put(login, gameCaretaker);
    }

    protected void removeGame(String login) {
        playerGame.remove(login);
        playerGameCaretaker.remove(login);
    }
}
