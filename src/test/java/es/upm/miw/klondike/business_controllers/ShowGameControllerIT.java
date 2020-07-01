package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.models.Card;
import es.upm.miw.klondike.models.Game;
import es.upm.miw.klondike.models.Rank;
import es.upm.miw.klondike.models.Suite;
import es.upm.miw.klondike.rest_controllers.OpenGameResource;
import es.upm.miw.klondike.rest_controllers.ShowGameResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ShowGameControllerIT extends MoveControllerIT {

    @Autowired
    private ShowGameController showGameController;

    @Test
    void testShowGame(){
        int idGame = 17;
        super.openGameForTest(idGame);
        GameDto gameDto = this.showGameController.showGame(super.test.getLogin());
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
    void testShowWaste(){
        int idGame = 17;
        super.openGameForTest(idGame);
        WasteDto wasteDto = this.showGameController.showWaste(super.test.getLogin());
        assertNotNull(wasteDto);
        assertNotNull(wasteDto.getCards());
        assertEquals(0, wasteDto.getCards().size());
    }

    @Test
    void testShowFoundations(){
        int idGame = 17;
        super.openGameForTest(idGame);
        List<FoundationDto> foundationsList = this.showGameController.showFoundations(super.test.getLogin());
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
    }

    @Test
    void testShowTableaus(){
        int idGame = 17;
        super.openGameForTest(idGame);
        List<TableauDto> tableausList = this.showGameController.showTableaus(super.test.getLogin());
        TableauDto tableauDto = tableausList.get(0);
        assertNotNull(tableauDto);
        assertEquals(1, tableauDto.getCards().size());
        CardDto expectedCardDto = new CardDto(new Card(Rank.KING, Suite.SPADES));
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
}