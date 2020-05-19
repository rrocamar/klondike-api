package es.upm.miw.klondike.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game OUTGame;
    private int initialStockCardsExpected = 24;
    @BeforeEach
    public void before(){
        System.out.println("Se ejecuta Before");
        OUTGame = new Game();
    }

    @Test
    void createCardTest() {
        System.out.println( this.OUTGame);
        int stockCards = 0;
        Stock stock = this.OUTGame.getStock();
        while(!stock.isEmpty()){
            stockCards++;
            Card card = stock.getCardOnTop();
            assertFalse(card.isUpturned());
        }
        assertEquals(initialStockCardsExpected,stockCards);
        Waste waste = this.OUTGame.getWaste();
        assertTrue(waste.isEmpty());
        for(int i=0;i<Game.NUMBER_OF_FOUNDATIONS;i++)
            assertTrue(OUTGame.getFoundation(i).isEmpty());
        for(int i=0;i<Game.NUMBER_OF_TABLEAUS;i++)
            assertFalse(OUTGame.getTableau(i).isEmpty());

        for(int i=0;i<Game.NUMBER_OF_TABLEAUS;i++) {
            Tableau tableau = OUTGame.getTableau(i);
            Card card = tableau.getCardOnTop();
            assertTrue(card.isUpturned());
            int cardsInTableauDownturned = 0;
            while(!tableau.isEmpty()){
                assertFalse(tableau.getCardOnTop().isUpturned());
                cardsInTableauDownturned++;
            }
            assertEquals(i,cardsInTableauDownturned);
        }
    }

}