package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private Stock OUTStock;
    private Stock OUTEmptyStock;
    private int numberOfCards;

    @BeforeEach
    public void before(){
        this.numberOfCards = new Random().nextInt(24)+1;
        List<Card> cards = new ArrayList<>();
        for(int j=0;j<this.numberOfCards;j++) {
            Card card = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
            cards.add(card);
        }
        this.OUTStock = new Stock(cards);
        this.OUTEmptyStock = new Stock(new ArrayList<>());
    }

    @Test
    void createStockTest() {
        assertFalse(this.OUTStock.isEmpty());
    }

    @Test
    void getCardOnTopTest() {
        Card randomCard = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
        randomCard.setUpturned(true);
        this.OUTEmptyStock.putCardOnTop(randomCard);
        assertFalse(this.OUTEmptyStock.isEmpty());
        assertEquals(randomCard,this.OUTEmptyStock.getCardOnTop());
        assertTrue(this.OUTEmptyStock.isEmpty());
        this.OUTStock.putCardOnTop(randomCard);
        assertFalse(this.OUTStock.isEmpty());
        assertEquals(randomCard,this.OUTStock.getCardOnTop());
        assertFalse(this.OUTStock.isEmpty());
    }

    @Test
    void isValidMoveEmptyFoundationTest() {
        Deck deck = new Deck();
        assertTrue(this.OUTEmptyStock.isEmpty());
        while(!deck.isEmpty()) {
            Card card = deck.getCard();
            card.setUpturned(false);
            assertTrue(this.OUTEmptyStock.isValidMove(card));
            card.setUpturned(true);
            assertFalse(this.OUTEmptyStock.isValidMove(card));
        }
    }

    @Test
    void cloneTest() {
        Stock copyStock = (Stock)this.OUTStock.clone();
        assertNotSame(copyStock, this.OUTStock);
        while(!this.OUTStock.isEmpty()){
            Card card = this.OUTStock.getCardOnTop();
            Card copyCard = copyStock.getCardOnTop();
            assertNotSame(copyCard, card);
            assertEquals(copyCard, card);
        }
        assertTrue(copyStock.isEmpty());
    }

}