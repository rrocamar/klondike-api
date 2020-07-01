package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.CardDto;
import es.upm.miw.klondike.dtos.FoundationDto;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.dtos.TableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import es.upm.miw.klondike.rest_controllers.MoveFromFoundationToTableauResource;
import es.upm.miw.klondike.rest_controllers.RedoMoveResource;
import es.upm.miw.klondike.rest_controllers.UndoMoveResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class RedoMoveControllerIT extends MoveControllerIT {

    @Autowired
    private RedoMoveController redoMoveController;

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
        this.moveFromFoundationToTableauController.move(moveDto, this.test.getLogin());
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
        this.redoMoveController.redo(super.test.getLogin());
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
        assertThrows(BadRequestException.class, () -> this.redoMoveController.redo(this.test.getLogin()));
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