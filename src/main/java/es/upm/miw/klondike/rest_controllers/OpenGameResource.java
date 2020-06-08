package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.OpenGameController;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(OpenGameResource.GAME)
public class OpenGameResource {

    public static final String GAME = "/game";
    public static final String OPEN = "/open/{id}";
    public static final String NAMES_OF_GAMES = "/names";

    @Autowired
    private OpenGameController openGameController;

    @GetMapping(value = OpenGameResource.NAMES_OF_GAMES)
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameMinimunDto> getAllGames() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.openGameController.getAllGames(login);
    }

    @PostMapping(value = OpenGameResource.OPEN)
    @ResponseStatus(value = HttpStatus.OK)
    public void openGame(@PathVariable Long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.openGameController.openGame(login, id);
    }
}