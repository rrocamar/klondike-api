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
class MoveFromWasteToFoundationResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 23;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        super.openGameForTest(idGame);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundation);
        super.testPileCards(foundationDto,0, null);
        WasteDto wasteDto = super.getWasteDto();
        CardDto cardOnTop = new CardDto(new Card(Rank.ACE, Suite.HEARTS));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 20, cardOnTop);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromWasteToFoundationResource.MOVES +  MoveFromWasteToFoundationResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isOk();
        foundationDto = super.getFoundationDto(numberOfFoundation);
        super.testPileCards(foundationDto,1, cardOnTop);
        wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 19, null);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 13;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        super.openGameForTest(idGame);
        FoundationDto foundationDto = super.getFoundationDto(numberOfFoundation);
        super.testPileCards(foundationDto,0, null);
        WasteDto wasteDto = super.getWasteDto();
        CardDto cardOnTop = new CardDto(new Card(Rank.THREE, Suite.HEARTS));
        cardOnTop.setUpturned(true);
        super.testPileCards(wasteDto, 12, cardOnTop);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromWasteToFoundationResource.MOVES +  MoveFromWasteToFoundationResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        foundationDto = super.getFoundationDto(numberOfFoundation);
        super.testPileCards(foundationDto,0, null);
        wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 12, cardOnTop);
    }

    @Test
    void testMoveWithInvalidPlayer() {
        int idGame = 13;
        int numberOfFoundation = 3;
        MoveWasteToFoundationDto moveDto = new MoveWasteToFoundationDto();
        moveDto.setFoundationDestination(numberOfFoundation);
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromWasteToFoundationResource.MOVES +  MoveFromWasteToFoundationResource.TYPE_MOVE)
                .body(BodyInserters.fromObject(moveDto))
                .exchange()
                .expectStatus().isUnauthorized();
    }
}