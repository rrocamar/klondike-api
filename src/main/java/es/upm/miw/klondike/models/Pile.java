package es.upm.miw.klondike.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Pile {

    private List<Card> cards;

    public Pile(){
        this.cards = new ArrayList<>();
    }

    public Pile(List<Card> cards){
        this.cards = cards;
    }

    public boolean isEmpty(){
        return this.cards.isEmpty();
    }

    public Iterator<Card> getAllCards(){
        return this.cards.iterator();
    }

    public Card getCardOnTop(){
        assert this.cards.size()>0;
        return this.cards.remove(this.cards.size()-1);
    }

    protected Card viewCardOnTop(){
        assert this.cards.size()>0;
        return this.cards.get(this.cards.size()-1);
    }

    public void putCardOnTop(Card card){
        assert this.isValidMove(card);
        this.cards.add(card);
    }

    public abstract boolean isValidMove(Card card);

    @Override
    public String toString() {
        return "Pile{" +
                "cards=" + cards +
                '}';
    }
}
