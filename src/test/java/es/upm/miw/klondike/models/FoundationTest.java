package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FoundationTest {

    private Foundation OUTFoundation;
    private Foundation OUTEmptyFoundation;
    private Suite randomSuite;
    private int numberOfCards;

    @BeforeEach
    public void before(){
        this.numberOfCards = new Random().nextInt(11)+1;
        randomSuite = Suite.values()[new Random().nextInt(4)];
        List<Card> cards = new ArrayList<>();
        for(int j=0;j<this.numberOfCards;j++) {
            Card card = new Card(Rank.values()[j], randomSuite);
            card.setUpturned(true);
            cards.add(card);
        }
        this.OUTEmptyFoundation = new Foundation(new ArrayList<>());
        this.OUTFoundation = new Foundation(cards);
    }

    @Test
    void createFoundationTest() {
        assertFalse(this.OUTFoundation.isEmpty());
        assertTrue(this.OUTEmptyFoundation.isEmpty());
    }

    @Test
    void getCardOnTopTest() {
        Card randomCard = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
        randomCard.setUpturned(true);
        this.OUTEmptyFoundation.putCardOnTop(randomCard);
        assertFalse(this.OUTEmptyFoundation.isEmpty());
        assertEquals(randomCard,this.OUTEmptyFoundation.getCardOnTop());
        assertTrue(this.OUTEmptyFoundation.isEmpty());
        //assertThrows(Error.class, ()-> this.OUTEmptyFoundation.getCardOnTop());
        this.OUTFoundation.putCardOnTop(randomCard);
        assertFalse(this.OUTFoundation.isEmpty());
        assertEquals(randomCard,this.OUTFoundation.getCardOnTop());
        assertFalse(this.OUTFoundation.isEmpty());
    }

    @Test
    void isValidMoveEmptyFoundationTest() {
        Deck deck = new Deck();
        assertTrue(this.OUTEmptyFoundation.isEmpty());
        while(!deck.isEmpty()) {
            Card card = deck.getCard();
            card.setUpturned(true);
            if(card.getRank().equals(Rank.ACE))
                assertTrue(this.OUTEmptyFoundation.isValidMove(card));
            else
                assertFalse(this.OUTEmptyFoundation.isValidMove(card));
        }
    }

    @Test
    void isValidMoveFoundationTest() {
        Deck deck = new Deck();
        assertFalse(this.OUTFoundation.isEmpty());
        while(!deck.isEmpty()) {
            Card card = deck.getCard();
            card.setUpturned(true);
            if(card.getRank().equals(Rank.values()[numberOfCards]) && card.getSuite().equals(this.randomSuite))
                assertTrue(this.OUTFoundation.isValidMove(card));
            else
                assertFalse(this.OUTFoundation.isValidMove(card));
        }
    }

    @Test
    void isValidMoveFullFoundationTest() {
        for(int j=0;j<Rank.values().length;j++) {
            Card card = new Card(Rank.values()[j], randomSuite);
            card.setUpturned(true);
            this.OUTEmptyFoundation.putCardOnTop(card);
        }
        Deck deck = new Deck();
        while(!deck.isEmpty()) {
            Card card = deck.getCard();
            card.setUpturned(true);
            assertFalse(this.OUTEmptyFoundation.isValidMove(card));
        }
    }

    @Test
    void cloneTest() {
        Foundation copyFoundation = (Foundation)this.OUTFoundation.clone();
        assertNotSame(copyFoundation, this.OUTFoundation);
        while(!this.OUTFoundation.isEmpty()){
            Card card = this.OUTFoundation.getCardOnTop();
            Card copyCard = copyFoundation.getCardOnTop();
            assertNotSame(copyCard, card);
            assertEquals(copyCard, card);
        }
        assertTrue(copyFoundation.isEmpty());
    }
}