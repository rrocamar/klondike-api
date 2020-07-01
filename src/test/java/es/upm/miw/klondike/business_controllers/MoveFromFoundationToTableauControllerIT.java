package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MoveFromFoundationToTableauControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromFoundationToTableauController moveFromFoundationToTableauController;

    @Test
    void testValidMove(){
        long idGame = 25;
        super.openGameForTest(idGame);
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        MoveFoundationToTableauDto moveDto = new MoveFoundationToTableauDto();
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setFoundationSource(numberOfFoundationSource);
        Tableau tableau = super.getTableau(numberOfTableauDestination);
        Card cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
        this.moveFromFoundationToTableauController.move(moveDto, this.test.getLogin());
        tableau = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,14, cardOnTop);
        foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.ACE, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 1, cardOnTop);
    }

    @Test
    void testInvalidMove(){
        long idGame = 25;
        super.openGameForTest(idGame);
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        MoveFoundationToTableauDto moveDto = new MoveFoundationToTableauDto();
        moveDto.setTableauDestination(numberOfTableauDestination+1);
        moveDto.setFoundationSource(numberOfFoundationSource);
        Tableau tableau = super.getTableau(numberOfTableauDestination);
        Card cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
        assertThrows(BadRequestException.class, () -> this.moveFromFoundationToTableauController.move(moveDto, this.test.getLogin()));
        tableau = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
    }
}