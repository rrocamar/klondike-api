package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.GameCaretaker;
import es.upm.miw.klondike.models.GameMemento;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;

@Controller
public class MoveFromFoundationToTableauController extends GameController {

    public void move(MoveFoundationToTableauDto moveDto, String login) {
        Game game = getGame(login);
        if(game.getFoundation(moveDto.getFoundationSource()).isEmpty())
            throw new BadRequestException("Foundation source is empty: invalid move.");
        Card card = game.getFoundation(moveDto.getFoundationSource()).getCardOnTop();
        if(!game.getTableau(moveDto.getTableauDestination()).isValidMove(card)){
            game.getFoundation(moveDto.getFoundationSource()).putCardOnTop(card);
            throw new BadRequestException("Tableau destination: invalid move");
        }
        game.getTableau(moveDto.getTableauDestination()).putCardOnTop(card);
        GameCaretaker gameCaretaker = this.getGameCaretaker(login);
        GameMemento gameMemento = game.createMemento();
        gameCaretaker.addMemento(gameMemento);
    }
}
