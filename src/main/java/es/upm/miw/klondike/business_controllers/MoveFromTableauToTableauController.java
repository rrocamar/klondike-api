package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.dtos.MoveTableauToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.Tableau;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.Stack;

@Controller
public class MoveFromTableauToTableauController  extends GameController {

    private void putCardsOnTableau(Tableau tableau, Stack<Card>cards){
        while(!cards.isEmpty())
            tableau.putCardOnTop(cards.pop());
    }

    public void move(MoveTableauToTableauDto moveDto, HttpSession session) {
        Game game = getGame(session);
        Stack<Card> cardStack = new Stack<>();
        for(int i=1; i <= moveDto.getNumberOfCards(); i++){
            if(game.getTableau(moveDto.getTableauSource()).isEmpty()){
                putCardsOnTableau(game.getTableau(moveDto.getTableauSource()), cardStack);
                throw new BadRequestException("Tableau source is empty: invalid move.");
            }
            Card card = game.getTableau(moveDto.getTableauSource()).getCardOnTop();
            cardStack.push(card);
            if(!card.isUpturned()){
                putCardsOnTableau(game.getTableau(moveDto.getTableauSource()), cardStack);
                throw new BadRequestException("Tableau source card is downturned: invalid move.");
            }
        }
        while(!cardStack.isEmpty()) {
            Card card = cardStack.pop();
            if (!game.getTableau(moveDto.getTableauDestination()).isValidMove(card)) {
                cardStack.push(card);
                putCardsOnTableau(game.getTableau(moveDto.getTableauSource()), cardStack);
                throw new BadRequestException("Tableau destination: invalid move");
            }
            game.getTableau(moveDto.getTableauDestination()).putCardOnTop(card);
        }
        if(!game.getTableau(moveDto.getTableauSource()).isEmpty()){
            Card card = game.getTableau(moveDto.getTableauSource()).getCardOnTop();
            card.setUpturned(true);
            game.getTableau(moveDto.getTableauSource()).putCardOnTop(card);
        }
    }
}
