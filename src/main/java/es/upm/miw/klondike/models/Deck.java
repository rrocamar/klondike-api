package es.upm.miw.klondike.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(){
        this.cards = new ArrayList<>();
        for(int i=0;i<Suite.values().length;i++)
            for(int j=0;j<Rank.values().length;j++)
                cards.add(new Card(Rank.values()[j],Suite.values()[i]));
    }

    public Card getCard(){
        assert cards.size()>0;
        return cards.remove(0);
    }

    public boolean isEmpty(){
        return this.cards.isEmpty();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public List<Card> getCards(int numberOfCards){
        assert this.cards.size()>= numberOfCards;
        ArrayList<Card> resultCards = new ArrayList<>();
        for(int i=0;i<numberOfCards;i++)
            resultCards.add(this.cards.remove(0));
        return resultCards;
    }
}
