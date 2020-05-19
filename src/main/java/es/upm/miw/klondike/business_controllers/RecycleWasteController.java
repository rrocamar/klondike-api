package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class RecycleWasteController extends GameController{

    public void move(HttpSession session) {
        Game game = getGame(session);
        if(game == null)
            throw new NotFoundException("Game");
        if(!game.getStock().isEmpty()||game.getWaste().isEmpty())
            throw new BadRequestException("Invalid Recycle Waste");
        while(!game.getWaste().isEmpty()){
            Card card = game.getWaste().getCardOnTop();
            card.setUpturned(false);
            game.getStock().putCardOnTop(card);
        }
    }
}
