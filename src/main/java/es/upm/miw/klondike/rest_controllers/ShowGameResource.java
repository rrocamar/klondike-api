package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.ShowGameController;
import es.upm.miw.klondike.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(ShowGameResource.SHOW_GAME)
public class ShowGameResource {

    public static final String SHOW_GAME = "/game";

    public static final String STOCK = "/stock";
    public static final String WASTE = "/waste";
    public static final String TABLEAUS = "/tableaus";
    public static final String FOUNDATIONS = "/foundations";
    public static final String STATUS = "/status";

    @Autowired
    private ShowGameController showGameController;

    @GetMapping(value = "/",produces = "application/json" )
    public GameDto showGame() {
        return showGameController.showGame(this.getLogin());
    }

    @GetMapping(value = ShowGameResource.STOCK,produces = "application/json" )
    public StockDto showStock() {
        return showGameController.showStock(this.getLogin());
    }

    @GetMapping(value = ShowGameResource.WASTE,produces = "application/json" )
    public WasteDto showWaste() {
        return showGameController.showWaste(this.getLogin());
    }

    @GetMapping(value = ShowGameResource.FOUNDATIONS,produces = "application/json" )
    public List<FoundationDto> showFoundations() {
        return showGameController.showFoundations(this.getLogin());
    }

    @GetMapping(value = ShowGameResource.TABLEAUS,produces = "application/json" )
    public List<TableauDto> showTableaus() {
        return showGameController.showTableaus(this.getLogin());
    }

    @GetMapping(value = ShowGameResource.STATUS,produces = "application/json" )
    public GameStatusDto showStatus() {
        return showGameController.showGameStatus(this.getLogin());
    }

    protected String getLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
