package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.NewGameController;
import es.upm.miw.klondike.dtos.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(NewGameResource.GAME)
public class NewGameResource {

    public static final String GAME = "/game";

    public static final String NEW = "/new";

    @Autowired
    private NewGameController newGameController;

    @PostMapping(value = NEW)
    @ResponseStatus(value = HttpStatus.OK)
    public void newGame() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.newGameController.createNewGame(login);
    }
}
