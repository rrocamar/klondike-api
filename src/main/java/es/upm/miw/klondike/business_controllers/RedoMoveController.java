package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;
import org.springframework.stereotype.Controller;

@Controller
public class RedoMoveController extends GameController{

    public void redo(String login) {
        Game game = getGame(login);
        GameCaretaker gameCaretaker = getGameCaretaker(login);
        if(!gameCaretaker.isRedoable())
            throw new BadRequestException("No redo moves availables.");
        game.restoreMemento(gameCaretaker.getRedoMemento());
    }
}
