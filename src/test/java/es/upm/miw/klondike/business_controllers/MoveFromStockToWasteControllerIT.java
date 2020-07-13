package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MoveFromStockToWasteControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromStockToWasteController moveFromStockToWasteController;

    @Test
    void testValidMove(){
        long idGame = 11;
        super.openGameForTest(idGame);
        Waste waste = super.getWaste();
        Card cardOnTopWaste = new Card(Rank.TEN, Suite.SPADES);
        cardOnTopWaste.setUpturned(true);
        this.testPileCards(waste,3, cardOnTopWaste);
        Stock stock = super.getStock();
        Card cardOnTopStock = new Card(Rank.QUEEN, Suite.SPADES);
        this.testPileCards(stock, 16, cardOnTopStock);
        this.moveFromStockToWasteController.move(super.test.getLogin());
        waste = super.getWaste();
        cardOnTopWaste = new Card(Rank.QUEEN, Suite.SPADES);
        cardOnTopWaste.setUpturned(true);
        this.testPileCards(waste, 4, cardOnTopWaste);
    }

    @Test
    void testInvalidMove(){
        int idGame = 22;
        super.openGameForTest(idGame);
        Stock stock = super. getStock();
        this.testPileCards(stock,0,null);
        assertThrows(BadRequestException.class, () -> this.moveFromStockToWasteController.move(super.test.getLogin()));
    }
}