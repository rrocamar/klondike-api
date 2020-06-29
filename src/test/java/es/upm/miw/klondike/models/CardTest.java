package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Card OUTCard;

    @BeforeEach
    public void before(){
        OUTCard = new Card(Rank.values()[new Random().nextInt(13)], Suite.values()[new Random().nextInt(4)]);
    }

    @Test
    void createCardTest() {
        assertFalse(this.OUTCard.isUpturned());
    }

    @Test
    void createCloneTest() {
        this.OUTCard.setUpturned(new Random().nextBoolean());
        Card copy = (Card)this.OUTCard.clone();
        assertEquals(copy.getRank(), this.OUTCard.getRank());
        assertEquals(copy.getSuite(), this.OUTCard.getSuite());
        assertEquals(copy.isUpturned(), this.OUTCard.isUpturned());
    }

}