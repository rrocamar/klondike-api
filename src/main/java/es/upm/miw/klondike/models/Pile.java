package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Pile implements Cloneable, Serializable {

    private List<Card> cards;

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
        this.cards.add(card);
    }

    public abstract boolean isValidMove(Card card);

    @Override
    public Object clone() {
        Pile copy = null;
        try {
            copy = (Pile) super.clone();
        }catch(CloneNotSupportedException ex){

        }
        List<Card> listOfCopyCards = new ArrayList<>();
        copy.cards = listOfCopyCards;
        Iterator<Card>iteratorCards = this.getAllCards();
        while(iteratorCards.hasNext())
            listOfCopyCards.add((Card)iteratorCards.next().clone());
        return copy;
    }

    @Override
    public String toString() {
        return "Pile{" +
                "cards=" + cards +
                '}';
    }
}
