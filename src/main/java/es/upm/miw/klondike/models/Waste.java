package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.List;

public class Waste extends Pile implements Serializable {

    public Waste(List<Card> cards) {
        super(cards);
    }

    @Override
    public boolean isValidMove(Card card) {
        return true;
    }
}
