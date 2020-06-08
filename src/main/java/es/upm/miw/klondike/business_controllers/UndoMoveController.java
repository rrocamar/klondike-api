package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;
import org.springframework.stereotype.Controller;

@Controller
public class UndoMoveController extends GameController{

    public void undo(String login) {
        Game game = getGame(login);
        GameCaretaker gameCaretaker = getGameCaretaker(login);
        if(!gameCaretaker.isUndoable())
            throw new BadRequestException("No undo moves availables.");
        game.restoreMemento(gameCaretaker.getUndoMemento());
    }
}
