package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveTableauToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Rank;
import es.upm.miw.klondike.models.Suite;
import es.upm.miw.klondike.models.Tableau;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MoveFromTableauToTableauControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromTableauToTableauController moveFromTableauToTableauController;

    @Test
    void testValidMove(){
        int idGame = 13;
        int numberOfTableauSource = 5;
        int numberOfTableauDestination = 0;
        int numberOfCards = 2;
        MoveTableauToTableauDto moveDto = new MoveTableauToTableauDto();
        moveDto.setTableauSource(numberOfTableauSource);
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setNumberOfCards(numberOfCards);
        super.openGameForTest(idGame);
        Tableau tableauSource = super.getTableau(numberOfTableauSource);
        Card cardOnTop = new Card(Rank.SEVEN, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSource,7, cardOnTop);
        Tableau tableauDestination = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.NINE, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestination, 5, cardOnTop);
        this.moveFromTableauToTableauController.move(moveDto, super.test.getLogin());
        tableauSource = super.getTableau(numberOfTableauSource);
        super.testPileCards(tableauSource,5, null);
        tableauDestination = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.SEVEN, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestination, 7, cardOnTop);
    }

    @Test
    void testInvalidMove(){
        int idGame = 13;
        int numberOfTableauSource = 3;
        int numberOfTableauDestination = 0;
        int numberOfCards = 2;
        MoveTableauToTableauDto moveDto = new MoveTableauToTableauDto();
        moveDto.setTableauSource(numberOfTableauSource);
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setNumberOfCards(numberOfCards);
        super.openGameForTest(idGame);
        Tableau tableauSource = super.getTableau(numberOfTableauSource);
        Card cardOnTop = new Card(Rank.FIVE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSource,7, cardOnTop);
        Tableau tableauDestination = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.NINE, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestination, 5, cardOnTop);
        assertThrows(BadRequestException.class, () -> this.moveFromTableauToTableauController.move(moveDto, super.test.getLogin()));
        tableauSource = super.getTableau(numberOfTableauSource);
        cardOnTop = new Card(Rank.FIVE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSource,7, cardOnTop);
        tableauDestination = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.NINE, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestination, 5, cardOnTop);
    }
}