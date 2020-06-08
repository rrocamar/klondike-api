package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;
import es.upm.miw.klondike.models.GameMemento;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class RecycleWasteController extends GameController{

    public void move(String login) {
        Game game = getGame(login);
        if(game == null)
            throw new NotFoundException("Game");
        if(!game.getStock().isEmpty()||game.getWaste().isEmpty())
            throw new BadRequestException("Invalid Recycle Waste");
        while(!game.getWaste().isEmpty()){
            Card card = game.getWaste().getCardOnTop();
            card.setUpturned(false);
            game.getStock().putCardOnTop(card);
        }
        GameCaretaker gameCaretaker = getGameCaretaker(login);
        GameMemento gameMemento = game.createMemento();
        gameCaretaker.addMemento(gameMemento);
    }
}
