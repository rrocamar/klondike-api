package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.List;

public class Foundation extends Pile implements Serializable {

    public Foundation(List<Card> cards){
        super(cards);
    }

    @Override
    public boolean isValidMove(Card card) {
        if(!this.isEmpty()){
            Card cardOnTop = viewCardOnTop();
            return cardOnTop.getSuite() == card.getSuite() && card.getRank().ordinal() == cardOnTop.getRank().ordinal()+1;
        }
        return card.getRank() == Rank.ACE;
    }
}
