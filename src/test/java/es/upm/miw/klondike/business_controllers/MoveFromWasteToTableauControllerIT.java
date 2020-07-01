package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MoveFromWasteToTableauControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromWasteToTableauController moveFromWasteToTableauController;

    @Test
    void testValidMove(){
        int idGame = 24;
        int numberOfTableau = 3;
        MoveWasteToTableauDto moveDto = new MoveWasteToTableauDto();
        moveDto.setTableauDestination(numberOfTableau);
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableau);
        Card cardOnTop = new Card(Rank.FOUR, Suite.DIAMONDS);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,2, cardOnTop);
        Waste waste = super.getWaste();
        cardOnTop = new Card(Rank.THREE, Suite.CLUBS);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 4, cardOnTop);
        this.moveFromWasteToTableauController.move(moveDto, super.test.getLogin());
        tableau = super.getTableau(numberOfTableau);
        cardOnTop = new Card(Rank.THREE, Suite.CLUBS);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,3, cardOnTop);
        waste = super.getWaste();
        cardOnTop = new Card(Rank.QUEEN, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 3, cardOnTop);
    }

    @Test
    void testInvalidMove(){
        int idGame = 24;
        int numberOfTableau = 4;
        MoveWasteToTableauDto moveDto = new MoveWasteToTableauDto();
        moveDto.setTableauDestination(numberOfTableau);
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableau);
        Card cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        Waste waste = super.getWaste();
        cardOnTop = new Card(Rank.THREE, Suite.CLUBS);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 4, cardOnTop);
        assertThrows(BadRequestException.class, () -> this.moveFromWasteToTableauController.move(moveDto, super.test.getLogin()));
        cardOnTop = new Card(Rank.THREE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,13, cardOnTop);
        waste = super.getWaste();
        cardOnTop = new Card(Rank.THREE, Suite.CLUBS);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 4, cardOnTop);
    }
}