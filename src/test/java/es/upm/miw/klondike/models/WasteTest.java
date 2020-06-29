package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WasteTest {

    private Waste OUTWaste;
    private Waste OUTEmptyWaste;
    private int numberOfCards;

    @BeforeEach
    public void before(){
        this.numberOfCards = new Random().nextInt(24)+1;
        List<Card> cards = new ArrayList<>();
        for(int j=0;j<this.numberOfCards;j++) {
            Card card = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
            cards.add(card);
        }
        this.OUTWaste = new Waste(cards);
        this.OUTEmptyWaste = new Waste(new ArrayList<>());
    }

    @Test
    void createStockTest() {
        assertFalse(this.OUTWaste.isEmpty());
    }

    @Test
    void getCardOnTopTest() {
        Card randomCard = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
        randomCard.setUpturned(true);
        this.OUTEmptyWaste.putCardOnTop(randomCard);
        assertFalse(this.OUTEmptyWaste.isEmpty());
        assertEquals(randomCard,this.OUTEmptyWaste.getCardOnTop());
        assertTrue(this.OUTEmptyWaste.isEmpty());
        //assertThrows(Error.class, ()-> this.OUTEmptyFoundation.getCardOnTop());
        this.OUTWaste.putCardOnTop(randomCard);
        assertFalse(this.OUTWaste.isEmpty());
        assertEquals(randomCard,this.OUTWaste.getCardOnTop());
        assertFalse(this.OUTWaste.isEmpty());
    }

    @Test
    void isValidMoveEmptyFoundationTest() {
        Deck deck = new Deck();
        assertTrue(this.OUTEmptyWaste.isEmpty());
        while(!deck.isEmpty()) {
            Card card = deck.getCard();
            card.setUpturned(true);
            assertTrue(this.OUTEmptyWaste.isValidMove(card));
            card.setUpturned(false);
            assertFalse(this.OUTEmptyWaste.isValidMove(card));
        }
    }

    @Test
    void cloneTest() {
        Waste copyWaste = (Waste)this.OUTWaste.clone();
        assertNotSame(copyWaste, this.OUTWaste);
        while(!this.OUTWaste.isEmpty()){
            Card card = this.OUTWaste.getCardOnTop();
            Card copyCard = copyWaste.getCardOnTop();
            assertNotSame(copyCard, card);
            assertEquals(copyCard, card);
        }
        assertTrue(copyWaste.isEmpty());
    }

}