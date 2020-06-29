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
class MoveFromTableauToFoundationResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 16;
        int numberOfTableauSource = 4;
        int numberOfFoundationDestination = 1;
        MoveTableauToFoundationDto moveDto = new MoveTableauToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundationDestination);
        moveDto.setTableauSource(numberOfTableauSource);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableauSource);
        CardDto cardOnTop = new CardDto(new Card(Rank.ACE, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,5, cardOnTop);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundationDestination);
        this.testPileCards(foundationDto, 0, null);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromTableauToFoundationResource.MOVES +  MoveFromTableauToFoundationResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isOk();
        tableauDto = super.getTableauDto(numberOfTableauSource);
        super.testPileCards(tableauDto,4, null);
        foundationDto = super.getFoundationDto(numberOfFoundationDestination);
        this.testPileCards(foundationDto, 1, cardOnTop);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 16;
        int numberOfTableauSource = 3;
        int numberOfFoundationDestination = 1;
        MoveTableauToFoundationDto moveDto = new MoveTableauToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundationDestination);
        moveDto.setTableauSource(numberOfTableauSource);
        super.openGameForTest(idGame);
        TableauDto tableauDto = super.getTableauDto(numberOfTableauSource);
        CardDto cardOnTop = new CardDto(new Card(Rank.TEN, Suite.SPADES));
        cardOnTop.setUpturned(true);
        super.testPileCards(tableauDto,4, cardOnTop);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundationDestination);
        this.testPileCards(foundationDto, 0, null);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromTableauToFoundationResource.MOVES +  MoveFromTableauToFoundationResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        tableauDto = super.getTableauDto(numberOfTableauSource);
        super.testPileCards(tableauDto,4, cardOnTop);
        foundationDto = super.getFoundationDto(numberOfFoundationDestination);
        this.testPileCards(foundationDto, 0, null);

    }

    @Test
    void testMoveWithInvalidPlayer() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromTableauToFoundationResource.MOVES +  MoveFromTableauToFoundationResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}