package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
abstract class MoveResourceIT {

    @Autowired
    protected RestService restService;

    @Autowired
    protected WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    protected void testPileCards(PileDto pileDto, int expectedNumberOfCardsOnPile, CardDto expectedCardOnTop){
        assertNotNull(pileDto);
        assertNotNull(pileDto.getCards());
        assertEquals(expectedNumberOfCardsOnPile, pileDto.getCards().size());
        if(expectedCardOnTop!=null)
            assertEquals(expectedCardOnTop, pileDto.getCards().get(pileDto.getCards().size()-1));
    }

    protected WasteDto getWasteDto() {
        WasteDto wasteDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  ShowGameResource.WASTE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(WasteDto.class)
                .returnResult().getResponseBody();
        return wasteDto;
    }
    protected StockDto getStockDto() {
        StockDto stockDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME + ShowGameResource.STOCK)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockDto.class)
                .returnResult().getResponseBody();
        return stockDto;
    }

    protected FoundationDto getFoundationDto(int number) {
        GameDto gameDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  "/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameDto.class)
                .returnResult().getResponseBody();
        return gameDto.getFoundations().get(number);
    }

    protected TableauDto getTableauDto(int number) {
        GameDto gameDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  "/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameDto.class)
                .returnResult().getResponseBody();
        return gameDto.getTableaus().get(number);
    }

    protected void openGameForTest(int idGame){
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
    }

}
