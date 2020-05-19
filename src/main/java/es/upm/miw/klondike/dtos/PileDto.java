package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Pile;
import es.upm.miw.klondike.models.Waste;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PileDto {

    private List<CardDto> cards;

    public PileDto() {
        this.cards = new ArrayList<>();
    }

    public PileDto(Pile pile) {
        this();
        Iterator<Card> pileCards = pile.getAllCards();
        while (pileCards.hasNext())
            this.cards.add(new CardDto(pileCards.next()));
    }

    public List<CardDto> getCards() {
        return cards;
    }

    public void setCards(List<CardDto> cards) {
        this.cards = cards;
    }
}
