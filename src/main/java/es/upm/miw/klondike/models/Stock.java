package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.List;

public class Stock extends Pile  implements Serializable {

    public Stock(List<Card> cards){
        super(cards);
    }

    @Override
    public boolean isValidMove(Card card) {
        return !card.isUpturned();
    }
}
