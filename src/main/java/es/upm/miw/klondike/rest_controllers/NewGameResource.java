package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.NewGameController;
import es.upm.miw.klondike.dtos.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(NewGameResource.GAME)
public class NewGameResource {

    public static final String GAME = "/game";

    public static final String NEW = "/new";

    @Autowired
    private NewGameController newGameController;

    @PostMapping(value = NEW,produces = "application/json" )
    public GameDto newGame(HttpSession session) {
        return this.newGameController.createNewGame(session);
    }
}
