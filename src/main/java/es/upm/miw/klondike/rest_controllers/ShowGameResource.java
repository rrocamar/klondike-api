package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.ShowGameController;
import es.upm.miw.klondike.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(ShowGameResource.SHOW_GAME)
public class ShowGameResource {

    public static final String SHOW_GAME = "/game";

    public static final String STOCK = "/stock";
    public static final String WASTE = "/waste";
    public static final String TABLEAUS = "/tableaus";
    public static final String FOUNDATIONS = "/foundations";

    @Autowired
    private ShowGameController showGameController;

    @GetMapping(value = "/",produces = "application/json" )
    public GameDto showGame(HttpSession session) {
        //return this.newGameController.createNewGame(getAuthenticathedUser());
        return showGameController.showGame(session);
    }

    @GetMapping(value = ShowGameResource.STOCK,produces = "application/json" )
    public StockDto showStock(HttpSession session) {
        return showGameController.showStock(session);
    }

    @GetMapping(value = ShowGameResource.WASTE,produces = "application/json" )
    public WasteDto showWaste(HttpSession session) {
        return showGameController.showWaste(session);
    }

    @GetMapping(value = ShowGameResource.FOUNDATIONS,produces = "application/json" )
    public List<FoundationDto> showFoundations(HttpSession session) {
        return showGameController.showFoundations(session);
    }

    @GetMapping(value = ShowGameResource.TABLEAUS,produces = "application/json" )
    public List<TableauDto> showTableaus(HttpSession session) {
        return showGameController.showTableaus(session);
    }
}
