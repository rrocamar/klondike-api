package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Rank;
import es.upm.miw.klondike.models.Suite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class MoveFromStockToWasteResourceIT extends MoveResourceIT {

    @Test
    void testMoveWithValidPlayer(){
        int idGame = 11;
        super.openGameForTest(idGame);
        WasteDto wasteDto = super.getWasteDto();
        CardDto cardOnTopWaste = new CardDto(new Card(Rank.TEN, Suite.SPADES));
        cardOnTopWaste.setUpturned(true);
        this.testPileCards(wasteDto,3, cardOnTopWaste);
        StockDto stockDto = super.getStockDto();
        CardDto cardOnTopStock = new CardDto(new Card(Rank.QUEEN, Suite.SPADES));
        this.testPileCards(stockDto, 16, cardOnTopStock);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromStockToWasteResource.MOVES +  MoveFromStockToWasteResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isOk();
        wasteDto = super.getWasteDto();
        cardOnTopWaste = new CardDto(new Card(Rank.QUEEN, Suite.SPADES));
        cardOnTopWaste.setUpturned(true);
        this.testPileCards(wasteDto, 4, cardOnTopWaste);
    }

    @Test
    void testInvalidMoveWithValidPlayer(){
        int idGame = 22;
        super.openGameForTest(idGame);
        StockDto stockDto = super. getStockDto();
        this.testPileCards(stockDto,0,null);
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + MoveFromStockToWasteResource.MOVES +  MoveFromStockToWasteResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testMoveWithInvalidPlayer() {
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .post().uri(contextPath + MoveFromStockToWasteResource.MOVES +  MoveFromStockToWasteResource.TYPE_MOVE)
                .exchange()
                .expectStatus().isUnauthorized();
    }

}