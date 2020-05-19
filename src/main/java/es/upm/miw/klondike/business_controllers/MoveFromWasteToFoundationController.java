package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveWasteToFoundationDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class MoveFromWasteToFoundationController extends GameController {

    public void move(MoveWasteToFoundationDto moveDto, HttpSession session) {
        Game game = getGame(session);
        if(game.getWaste().isEmpty())
            throw new BadRequestException("Waste is empty: invalid move.");
        Card card = game.getWaste().getCardOnTop();
        if(!game.getFoundation(moveDto.getFoundationDestination()).isValidMove(card)){
            game.getWaste().putCardOnTop(card);
            throw new BadRequestException("Foundation destination: invalid move");
        }
        game.getFoundation(moveDto.getFoundationDestination()).putCardOnTop(card);
    }
}
