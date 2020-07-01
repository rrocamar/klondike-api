package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveWasteToFoundationDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class MoveFromWasteToFoundationControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromWasteToFoundationController moveFromWasteToFoundationController;

    @Test
    void testValidMove(){
        int idGame = 23;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        super.openGameForTest(idGame);
        Foundation foundation = super.getFoundation(numberOfFoundation);
        super.testPileCards(foundation,0, null);
        Waste waste = super.getWaste();
        Card cardOnTop = new Card(Rank.ACE, Suite.HEARTS);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 20, cardOnTop);
        this.moveFromWasteToFoundationController.move(moveDto, super.test.getLogin());
        foundation = super.getFoundation(numberOfFoundation);
        super.testPileCards(foundation,1, cardOnTop);
        waste = super.getWaste();
        super.testPileCards(waste, 19, null);
    }

    @Test
    void testInvalidMove(){
        int idGame = 13;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        super.openGameForTest(idGame);
        Foundation foundation = super.getFoundation(numberOfFoundation);
        super.testPileCards(foundation,0, null);
        Waste waste = super.getWaste();
        Card cardOnTop = new Card(Rank.THREE, Suite.HEARTS);
        cardOnTop.setUpturned(true);
        super.testPileCards(waste, 12, cardOnTop);
        assertThrows(BadRequestException.class, () -> this.moveFromWasteToFoundationController.move(moveDto, super.test.getLogin()));
        foundation = super.getFoundation(numberOfFoundation);
        super.testPileCards(foundation,0, null);
        waste = super.getWaste();
        super.testPileCards(waste, 12, cardOnTop);
    }
}