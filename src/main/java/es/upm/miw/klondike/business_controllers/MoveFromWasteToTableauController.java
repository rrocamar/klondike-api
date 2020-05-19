package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class MoveFromWasteToTableauController extends GameController {

    public void move(MoveWasteToTableauDto moveDto, HttpSession session) {
        Game game = getGame(session);
        if(game.getWaste().isEmpty())
            throw new BadRequestException("Waste is empty: invalid move.");
        Card card = game.getWaste().getCardOnTop();
        if(!game.getTableau(moveDto.getTableauDestination()).isValidMove(card)){
            game.getWaste().putCardOnTop(card);
            throw new BadRequestException("Tableau destination: invalid move");
        }
        game.getTableau(moveDto.getTableauDestination()).putCardOnTop(card);
    }
}
