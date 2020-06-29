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
class RecycleWasteResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 15;
        super.openGameForTest(idGame);
        StockDto stockDto = super.getStockDto();
        super.testPileCards(stockDto,0, null);
        WasteDto wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 13, null);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + RecycleWasteResource.MOVES +  RecycleWasteResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isOk();
        stockDto = super.getStockDto();
        super.testPileCards(stockDto,13, null);
        wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 0, null);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 24;
        super.openGameForTest(idGame);
        StockDto stockDto = super.getStockDto();
        super.testPileCards(stockDto,9, null);
        WasteDto wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 4, null);
        super.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + RecycleWasteResource.MOVES +  RecycleWasteResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        stockDto = super.getStockDto();
        super.testPileCards(stockDto,9, null);
        wasteDto = super.getWasteDto();
        super.testPileCards(wasteDto, 4, null);
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