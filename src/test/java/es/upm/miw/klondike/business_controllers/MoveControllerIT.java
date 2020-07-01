package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.daos.UserDao;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
abstract class MoveControllerIT {

    protected User test;

    @Autowired
    protected OpenGameController openGameController;

    @Autowired
    protected UserDao userDao;

    @BeforeEach
    void loadGame() {
        this.test = userDao.findByLogin("test").orElse(null);
    }

    protected void openGameForTest(long idGame){
        this.openGameController.openGame(this.test.getLogin(), idGame);
    }

    protected void testPileCards(Pile pile, int expectedNumberOfCardsOnPile, Card expectedCardOnTop){
        assertNotNull(pile);
        assertNotNull(pile.getAllCards());
        int numberOfCards = 0;
        Iterator it = pile.getAllCards();
        while(it.hasNext()) {
            it.next();
            numberOfCards++;
        }
        assertEquals(expectedNumberOfCardsOnPile, numberOfCards);
        if(expectedCardOnTop!=null) {
            Card card = pile.getCardOnTop();
            assertEquals(expectedCardOnTop, card);
            pile.putCardOnTop(card);
        }
    }

    protected Waste getWaste() {
        return this.openGameController.getGame(this.test.getLogin()).getWaste();
    }

    protected Stock getStock() {
        return this.openGameController.getGame(this.test.getLogin()).getStock();
    }

    protected Foundation getFoundation(int number) {
        return this.openGameController.getGame(this.test.getLogin()).getFoundation(number);
    }

    protected Tableau getTableau(int number) {
        return this.openGameController.getGame(this.test.getLogin()).getTableau(number);
    }
}
