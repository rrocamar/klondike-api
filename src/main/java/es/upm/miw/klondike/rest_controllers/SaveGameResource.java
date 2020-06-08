package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.SaveGameController;
import es.upm.miw.klondike.dtos.GameMinimunDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(SaveGameResource.GAME)
public class SaveGameResource {

    public static final String GAME = "/game";
    public static final String SAVE = "/save";
    @Autowired
    private SaveGameController saveGameController;

    @PostMapping(value = SaveGameResource.SAVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@Valid @RequestBody GameMinimunDto gameMinimunDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.saveGameController.save(login, gameMinimunDto);
    }
}
