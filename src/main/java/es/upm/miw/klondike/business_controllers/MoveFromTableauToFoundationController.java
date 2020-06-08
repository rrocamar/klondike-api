package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveTableauToFoundationDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;
import es.upm.miw.klondike.models.GameMemento;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class MoveFromTableauToFoundationController extends GameController {

    public void move(MoveTableauToFoundationDto moveDto, String login) {
        Game game = getGame(login);
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
        GameCaretaker gameCaretaker = this.getGameCaretaker(login);
        GameMemento gameMemento = game.createMemento();
        gameCaretaker.addMemento(gameMemento);
    }
}
