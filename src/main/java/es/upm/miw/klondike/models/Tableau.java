package es.upm.miw.klondike.models;

import java.util.List;

public class Tableau extends Pile{

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
