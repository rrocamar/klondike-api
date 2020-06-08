package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.List;

public class Tableau extends Pile implements Serializable {

    public Tableau(List<Card> cards){
        super(cards);
    }

    @Override
    public boolean isValidMove(Card card) {
        if(!isEmpty()){
            Card cardOnTop = viewCardOnTop();
            return card.getColor() != cardOnTop.getColor() && card.getRank().ordinal()+1 == cardOnTop.getRank().ordinal();
        }
        return  card.getRank()==Rank.KING;
    }
}
