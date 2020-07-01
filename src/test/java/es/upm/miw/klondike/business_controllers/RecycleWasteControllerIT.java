package es.upm.miw.klondike.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import es.upm.miw.klondike.dtos.StockDto;
import es.upm.miw.klondike.dtos.WasteDto;
import es.upm.miw.klondike.exceptions.BadRequestException;
import es.upm.miw.klondike.models.*;
import es.upm.miw.klondike.rest_controllers.RecycleWasteResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class RecycleWasteControllerIT extends MoveControllerIT {

    @Autowired
    private RecycleWasteController recycleWasteController;

    @Test
    void testValidMove(){
        int idGame = 15;
        super.openGameForTest(idGame);
        Stock stock = super.getStock();
        super.testPileCards(stock,0, null);
        Waste waste = super.getWaste();
        super.testPileCards(waste, 13, null);
        this.recycleWasteController.move(super.test.getLogin());
        stock = super.getStock();
        super.testPileCards(stock,13, null);
        waste = super.getWaste();
        super.testPileCards(waste, 0, null);
    }

    @Test
    void testInvalidMove(){
        int idGame = 24;
        super.openGameForTest(idGame);
        Stock stock = super.getStock();
        super.testPileCards(stock,9, null);
        Waste waste = super.getWaste();
        super.testPileCards(waste, 4, null);
        assertThrows(BadRequestException.class, () -> this.recycleWasteController.move(super.test.getLogin()));
        stock = super.getStock();
        super.testPileCards(stock,9, null);
        waste = super.getWaste();
        super.testPileCards(waste, 4, null);
    }
}