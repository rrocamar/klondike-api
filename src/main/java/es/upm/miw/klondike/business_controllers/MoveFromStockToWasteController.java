package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;

@Controller
public class MoveFromStockToWasteController extends GameController {

    public void move(HttpSession session) {
        Game game = getGame(session);
        if(game == null)
            throw new NotFoundException("Game");
        if(game.getStock().isEmpty())
            throw new BadRequestException("Stock is empty");
        Card card = game.getStock().getCardOnTop();
        card.setUpturned(true);
        game.getWaste().putCardOnTop(card);
    }
}
