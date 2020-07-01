package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Rank;
import es.upm.miw.klondike.models.Suite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ApiTestConfig
class ShowGameResourceIT {

    @Autowired
    private RestService restService;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Test
    void testShowGameWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        GameDto gameDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  "/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameDto.class)
                .returnResult().getResponseBody();
        assertNotNull(gameDto);
        StockDto stockDto = gameDto.getStock();
        assertNotNull(stockDto);
        assertNotNull(stockDto.getCards());
        assertEquals(24, stockDto.getCards().size());
        WasteDto wasteDto = gameDto.getWaste();
        assertNotNull(wasteDto);
        assertNotNull(wasteDto.getCards());
        assertEquals(0, wasteDto.getCards().size());
        List<FoundationDto> foundationsList = gameDto.getFoundations();
        FoundationDto foundationDto = foundationsList.get(0);
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
        foundationDto = foundationsList.get(1);
        assertNotNull(foundationDto);
        assertEquals(1, foundationDto.getCards().size());
        CardDto expectedCardDto = new CardDto(new Card(Rank.ACE, Suite.SPADES));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, foundationDto.getCards().get(0));
        foundationDto = foundationsList.get(2);
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
        foundationDto = foundationsList.get(3);
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
        List<TableauDto> tableausList = gameDto.getTableaus();
        TableauDto tableauDto = tableausList.get(0);
        assertNotNull(tableauDto);
        assertEquals(1, tableauDto.getCards().size());
        expectedCardDto = new CardDto(new Card(Rank.KING, Suite.SPADES));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(0));
        tableauDto = tableausList.get(1);
        assertNotNull(tableauDto);
        assertEquals(2, tableauDto.getCards().size());
        expectedCardDto = new CardDto(new Card(Rank.SEVEN, Suite.HEARTS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(0));
        tableauDto = tableausList.get(6);
        assertNotNull(tableauDto);
        assertEquals(9, tableauDto.getCards().size());
        expectedCardDto = new CardDto(new Card(Rank.QUEEN, Suite.DIAMONDS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(6));
        expectedCardDto = new CardDto(new Card(Rank.JACK, Suite.CLUBS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(7));
        expectedCardDto = new CardDto(new Card(Rank.TEN, Suite.HEARTS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(8));
        for(int i=0;i<6;i++)
            assertFalse(tableauDto.getCards().get(i).isUpturned());
    }

    @Test
    void testShowStockWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        StockDto stockDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  ShowGameResource.STOCK)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockDto.class)
                .returnResult().getResponseBody();
        assertNotNull(stockDto);
        assertNotNull(stockDto.getCards());
        assertEquals(24, stockDto.getCards().size());
    }

    @Test
    void testShowWasteWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        WasteDto wasteDto = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  ShowGameResource.WASTE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(WasteDto.class)
                .returnResult().getResponseBody();
        assertNotNull(wasteDto);
        assertNotNull(wasteDto.getCards());
        assertEquals(0, wasteDto.getCards().size());
    }

    @Test
    void testShowFoundationsWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        FoundationDto[] foundationsList;
        foundationsList = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  ShowGameResource.FOUNDATIONS)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FoundationDto[].class)
                .returnResult().getResponseBody();
        FoundationDto foundationDto = foundationsList[0];
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
        foundationDto = foundationsList[1];
        assertNotNull(foundationDto);
        assertEquals(1, foundationDto.getCards().size());
        CardDto expectedCardDto = new CardDto(new Card(Rank.ACE, Suite.SPADES));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, foundationDto.getCards().get(0));
        foundationDto = foundationsList[2];
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
        foundationDto = foundationsList[3];
        assertNotNull(foundationDto);
        assertEquals(0, foundationDto.getCards().size());
    }

    @Test
    void testShowTableausWithValidPlayer(){
        int idGame = 17;
        this.restService.loginPlayer(webTestClient)
                .post().uri(contextPath + OpenGameResource.GAME +  OpenGameResource.OPEN, idGame)
                .exchange()
                .expectStatus().isOk();
        TableauDto[] tableausList = this.restService.loginPlayer(webTestClient)
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  ShowGameResource.TABLEAUS)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TableauDto[].class)
                .returnResult().getResponseBody();
        TableauDto tableauDto = tableausList[0];
        assertNotNull(tableauDto);
        assertEquals(1, tableauDto.getCards().size());
        CardDto expectedCardDto = new CardDto(new Card(Rank.KING, Suite.SPADES));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(0));
        tableauDto = tableausList[1];
        assertNotNull(tableauDto);
        assertEquals(2, tableauDto.getCards().size());
        expectedCardDto = new CardDto(new Card(Rank.SEVEN, Suite.HEARTS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(0));
        tableauDto = tableausList[6];
        assertNotNull(tableauDto);
        assertEquals(9, tableauDto.getCards().size());
        expectedCardDto = new CardDto(new Card(Rank.QUEEN, Suite.DIAMONDS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(6));
        expectedCardDto = new CardDto(new Card(Rank.JACK, Suite.CLUBS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(7));
        expectedCardDto = new CardDto(new Card(Rank.TEN, Suite.HEARTS));
        expectedCardDto.setUpturned(true);
        assertEquals(expectedCardDto, tableauDto.getCards().get(8));
        for(int i=0;i<6;i++)
            assertFalse(tableauDto.getCards().get(i).isUpturned());
    }

    @Test
    void testShowGameWithInvalidPlayer(){
        webTestClient
                .mutate().filter(basicAuthentication("1", "kk")).build()
                .get().uri(contextPath + ShowGameResource.SHOW_GAME +  "/")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}