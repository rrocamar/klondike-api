package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck OUTDeck;

    @BeforeEach
    public void before(){
        OUTDeck = new Deck();
    }

    @Test
    void createCardTest() {
        assertFalse(this.OUTDeck.isEmpty());
    }

    @Test
    void getCardTest() {
        assertFalse(this.OUTDeck.isEmpty());
        for(int i=0;i<52;i++) {
            assertFalse(this.OUTDeck.isEmpty());
            Card card = this.OUTDeck.getCard();
            assertFalse(card.isUpturned());
            card.setUpturned(true);
            assertTrue(card.isUpturned());
        }
        assertTrue(this.OUTDeck.isEmpty());
    }

    @Test
    void shuffleTest(){
        Deck orderedDeck = new Deck();
        this.OUTDeck.shuffle();
        while(orderedDeck.getCard().equals(this.OUTDeck.getCard()));
        assertTrue(!this.OUTDeck.isEmpty());
    }

    @Test
    void getCardsTest(){
        int numberOfCards = 13;
        List<Card> cardList = this.OUTDeck.getCards(numberOfCards);
        assertEquals(numberOfCards, cardList.size());
        cardList = this.OUTDeck.getCards(numberOfCards);
        assertEquals(numberOfCards, cardList.size());
        cardList = this.OUTDeck.getCards(numberOfCards);
        assertEquals(numberOfCards, cardList.size());
        cardList = this.OUTDeck.getCards(numberOfCards);
        assertEquals(numberOfCards, cardList.size());
        assertTrue(this.OUTDeck.isEmpty());
    }
}