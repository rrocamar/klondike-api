package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveTableauToFoundationDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class MoveFromTableauToFoundationController extends GameController {

    public void move(MoveTableauToFoundationDto moveDto, HttpSession session) {
        Game game = getGame(session);
        if(game.getTableau(moveDto.getTableauSource()).isEmpty())
            throw new BadRequestException("Tableau source is empty: invalid move.");
        Card card = game.getTableau(moveDto.getTableauSource()).getCardOnTop();
        if(!game.getFoundation(moveDto.getFoundationDestination()).isValidMove(card)){
            game.getTableau(moveDto.getTableauSource()).putCardOnTop(card);
            throw new BadRequestException("Foundation destination: invalid move");
        }
        game.getFoundation(moveDto.getFoundationDestination()).putCardOnTop(card);
        if(!game.getTableau(moveDto.getTableauSource()).isEmpty()){
            card = game.getTableau(moveDto.getTableauSource()).getCardOnTop();
            card.setUpturned(true);
            game.getTableau(moveDto.getTableauSource()).putCardOnTop(card);
        }
    }
}
