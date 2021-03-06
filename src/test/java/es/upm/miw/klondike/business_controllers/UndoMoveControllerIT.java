package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class UndoMoveControllerIT extends MoveControllerIT {

    @Autowired
    private UndoMoveController undoMoveController;

    @Autowired
    private MoveFromFoundationToTableauController moveFromFoundationToTableauController;


    @Test
    void testValidMove(){
        int idGame = 25;
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        MoveFoundationToTableauDto moveDto = new MoveFoundationToTableauDto();
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setFoundationSource(numberOfFoundationSource);
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableauDestination);
        Card cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
        this.moveFromFoundationToTableauController.move(moveDto, super.test.getLogin());
        tableau = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,14, cardOnTop);
        foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.ACE, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 1, cardOnTop);
        this.undoMoveController.undo(super.test.getLogin());
        tableau = super.getTableau(numberOfTableauDestination);
        cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
    }

    @Test
    void testInvalidMove(){
        int idGame = 25;
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableauDestination);
        Card cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationSource);
        cardOnTop = new Card(Rank.TWO, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        this.testPileCards(foundation, 2, cardOnTop);
        assertThrows(BadRequestException.class, () -> this.undoMoveController.undo(this.test.getLogin()));
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