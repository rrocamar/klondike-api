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
class MoveFromTableauToTableauResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 13;
        int numberOfTableauSource = 5;
        int numberOfTableauDestination = 0;
        int numberOfCards = 2;
        MoveTableauToTableauDto moveDto = new MoveTableauToTableauDto();
        moveDto.setTableauSource(numberOfTableauSource);
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setNumberOfCards(numberOfCards);
        super.openGameForTest(idGame);
        TableauDto tableauSourceDto = super.getTableauDto(numberOfTableauSource);
        CardDto cardOnTop = new CardDto(new Card(Rank.SEVEN, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSourceDto,7, cardOnTop);
        TableauDto tableauDestinationDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.NINE, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestinationDto, 5, cardOnTop);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromTableauToTableauResource.MOVES +  MoveFromTableauToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isOk();
        tableauSourceDto = super.getTableauDto(numberOfTableauSource);
        super.testPileCards(tableauSourceDto,5, null);
        tableauDestinationDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.SEVEN, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestinationDto, 7, cardOnTop);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 13;
        int numberOfTableauSource = 3;
        int numberOfTableauDestination = 0;
        int numberOfCards = 2;
        MoveTableauToTableauDto moveDto = new MoveTableauToTableauDto();
        moveDto.setTableauSource(numberOfTableauSource);
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setNumberOfCards(numberOfCards);
        super.openGameForTest(idGame);
        TableauDto tableauSourceDto = super.getTableauDto(numberOfTableauSource);
        CardDto cardOnTop = new CardDto(new Card(Rank.FIVE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSourceDto,7, cardOnTop);
        TableauDto tableauDestinationDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.NINE, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestinationDto, 5, cardOnTop);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromTableauToTableauResource.MOVES +  MoveFromTableauToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        tableauSourceDto = super.getTableauDto(numberOfTableauSource);
        cardOnTop = new CardDto(new Card(Rank.FIVE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauSourceDto,7, cardOnTop);
        tableauDestinationDto = super.getTableauDto(numberOfTableauDestination);
        cardOnTop = new CardDto(new Card(Rank.NINE, Suite.DIAMONDS));
        cardOnTop.setUpturned(true);
        this.testPileCards(tableauDestinationDto, 5, cardOnTop);
    }

    @Test
    void testMoveWithInvalidPlayer() {
        int idGame = 13;
        int numberOfTableauSource = 5;
        int numberOfTableauDestination = 0;
        int numberOfCards = 2;
        MoveTableauToTableauDto moveDto = new MoveTableauToTableauDto();
        moveDto.setTableauSource(numberOfTableauSource);
        moveDto.setTableauDestination(numberOfTableauDestination);
        moveDto.setNumberOfCards(numberOfCards);
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromTableauToTableauResource.MOVES +  MoveFromTableauToTableauResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isUnauthorized();
    }
}