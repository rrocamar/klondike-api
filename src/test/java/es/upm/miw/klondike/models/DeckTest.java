package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck OUTDeck;

    @BeforeEach
    public void before(){
        System.out.println("Se ejecuta Before");
        OUTDeck = new Deck();
    }

    @Test
    void createCardTest() {
        assertFalse(this.OUTDeck.isEmpty());
    }

    @Test
    void getCardTest() {
        this.OUTDeck.shuffle();
        for(int i=0;i<52;i++) {
            assertFalse(this.OUTDeck.isEmpty());
            System.out.println(this.OUTDeck.getCard());
        }
        assertTrue(this.OUTDeck.isEmpty());
    }
}