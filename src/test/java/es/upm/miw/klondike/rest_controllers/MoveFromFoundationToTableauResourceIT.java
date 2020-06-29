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
class MoveFromFoundationToTableauResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 25;
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        MoveFoundationToTableauDto moveDto = new MoveFoundationToTableauDto();
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setFoundationSource(numberOfFoundationSource);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableauDestination);
        CardDto cardOnTop = new CardDto(new Card(Rank.THREE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,13, cardOnTop);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundationSource);
        cardOnTop = new CardDto(new Card(Rank.TWO, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(foundationDto, 2, cardOnTop);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromFoundationToTableauResource.MOVES +  MoveFromFoundationToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isOk();
        tableauDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.TWO, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,14, cardOnTop);
        foundationDto = super.getFoundationDto(numberOfFoundationSource);
        cardOnTop = new CardDto(new Card(Rank.ACE, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(foundationDto, 1, cardOnTop);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 25;
        int numberOfFoundationSource = 0;
        int numberOfTableauDestination = 4;
        MoveFoundationToTableauDto moveDto = new MoveFoundationToTableauDto();
        moveDto.setTableauDestination(numberOfTableauDestination+1);
        moveDto.setFoundationSource(numberOfFoundationSource);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableauDestination);
        CardDto cardOnTop = new CardDto(new Card(Rank.THREE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,13, cardOnTop);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundationSource);
        cardOnTop = new CardDto(new Card(Rank.TWO, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(foundationDto, 2, cardOnTop);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromFoundationToTableauResource.MOVES +  MoveFromFoundationToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        tableauDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.THREE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,13, cardOnTop);
        foundationDto = super.getFoundationDto(numberOfFoundationSource);
        cardOnTop = new CardDto(new Card(Rank.TWO, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(foundationDto, 2, cardOnTop);
    }

    @Test
    void testMoveWithInvalidPlayer() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromFoundationToTableauResource.MOVES +  MoveFromFoundationToTableauResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}