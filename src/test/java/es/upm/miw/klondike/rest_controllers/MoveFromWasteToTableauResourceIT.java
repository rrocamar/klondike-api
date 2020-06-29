package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Rank;
import es.upm.miw.klondike.models.Suite;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class MoveFromWasteToTableauResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 24;
        int numberOfTableau = 3;
        MoveWasteToTableauDto moveDto = new MoveWasteToTableauDto();
        moveDto.setTableauDestination(numberOfTableau);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableau);
        CardDto cardOnTop = new CardDto(new Card(Rank.FOUR, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,2, cardOnTop);
        WasteDto wasteDto = super.getWasteDto();
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.CLUBS));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 4, cardOnTop);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromWasteToTableauResource.MOVES +  MoveFromWasteToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isOk();
        tableauDto = super.getTableauDto(numberOfTableau);
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.CLUBS));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,3, cardOnTop);
        wasteDto = super.getWasteDto();
        cardOnTop = new CardDto(new Card(Rank.QUEEN, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 3, cardOnTop);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 24;
        int numberOfTableau = 4;
        MoveWasteToTableauDto moveDto = new MoveWasteToTableauDto();
        moveDto.setTableauDestination(numberOfTableau);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableau);
        CardDto cardOnTop = new CardDto(new Card(Rank.THREE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,13, cardOnTop);
        WasteDto wasteDto = super.getWasteDto();
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.CLUBS));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 4, cardOnTop);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromWasteToTableauResource.MOVES +  MoveFromWasteToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,13, cardOnTop);
        wasteDto = super.getWasteDto();
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.CLUBS));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 4, cardOnTop);
    }

    @Test
    void testMoveWithInvalidPlayer() {
        int idGame = 13;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromWasteToTableauResource.MOVES +  MoveFromWasteToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isUnauthorized();
    }
}