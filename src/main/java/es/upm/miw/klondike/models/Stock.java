package es.upm.miw.klondike.models;

import java.util.List;

public class Stock extends Pile {

    public Stock(List<Card> cards){
        super(cards);
    }

    @Override
    public boolean isValidMove(Card card) {
        return !card.isUpturned();
    }
}
