package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;

@Controller
public class MoveFromFoundationToTableauController extends GameController {

    public void move(MoveFoundationToTableauDto moveDto, HttpSession session) {
        Game game = getGame(session);
        if(game.getFoundation(moveDto.getFoundationSource()).isEmpty())
            throw new BadRequestException("Foundation source is empty: invalid move.");
        Card card = game.getFoundation(moveDto.getFoundationSource()).getCardOnTop();
        if(!game.getTableau(moveDto.getTableauDestination()).isValidMove(card)){
            game.getTableau(moveDto.getTableauDestination()).putCardOnTop(card);
            throw new BadRequestException("Tableau destination: invalid move");
        }
        game.getTableau(moveDto.getTableauDestination()).putCardOnTop(card);
    }
}
