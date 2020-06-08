package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.GameController;
import es.upm.miw.klondike.business_controllers.QuitGameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(QuitGameResource.GAME)
public class QuitGameResource extends GameController {

    public static final String GAME = "/game";
    public static final String QUIT = "/quit";

    @Autowired
    private QuitGameController quitGameController;

    @PostMapping(value = QuitGameResource.QUIT)
    @ResponseStatus(value = HttpStatus.OK)
    public void quitGame() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.quitGameController.quitGame(login);
    }
}
