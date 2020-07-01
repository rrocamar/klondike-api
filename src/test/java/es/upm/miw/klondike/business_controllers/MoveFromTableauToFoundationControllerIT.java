package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.CardDto;
import es.upm.miw.klondike.dtos.FoundationDto;
import es.upm.miw.klondike.dtos.MoveTableauToFoundationDto;
import es.upm.miw.klondike.dtos.TableauDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import es.upm.miw.klondike.rest_controllers.MoveFromTableauToFoundationResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class MoveFromTableauToFoundationControllerIT extends MoveControllerIT {

    @Autowired
    private MoveFromTableauToFoundationController moveFromTableauToFoundationController;

    @Test
    void testValidMove(){
        int idGame = 16;
        int numberOfTableauSource = 4;
        int numberOfFoundationDestination = 1;
        MoveTableauToFoundationDto moveDto = new MoveTableauToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundationDestination);
        moveDto.setTableauSource(numberOfTableauSource);
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableauSource);
        Card cardOnTop = new Card(Rank.ACE, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,5, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationDestination);
        this.testPileCards(foundation, 0, null);
        this.moveFromTableauToFoundationController.move(moveDto, super.test.getLogin());
        tableau = super.getTableau(numberOfTableauSource);
        super.testPileCards(tableau,4, null);
        foundation = super.getFoundation(numberOfFoundationDestination);
        this.testPileCards(foundation, 1, cardOnTop);
    }

    @Test
    void testInvalidMove(){
        int idGame = 16;
        int numberOfTableauSource = 3;
        int numberOfFoundationDestination = 1;
        MoveTableauToFoundationDto moveDto = new MoveTableauToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundationDestination);
        moveDto.setTableauSource(numberOfTableauSource);
        super.openGameForTest(idGame);
        Tableau tableau = super.getTableau(numberOfTableauSource);
        Card cardOnTop = new Card(Rank.TEN, Suite.SPADES);
        cardOnTop.setUpturned(true);
        super.testPileCards(tableau,4, cardOnTop);
        Foundation foundation = super.getFoundation(numberOfFoundationDestination);
        this.testPileCards(foundation, 0, null);
        assertThrows(BadRequestException.class, () -> this.moveFromTableauToFoundationController.move(moveDto, super.test.getLogin()));
        tableau = super.getTableau(numberOfTableauSource);
        super.testPileCards(tableau,4, cardOnTop);
        foundation = super.getFoundation(numberOfFoundationDestination);
        this.testPileCards(foundation, 0, null);
    }
}