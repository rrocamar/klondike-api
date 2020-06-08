package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromWasteToTableauController;
import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(MoveFromWasteToTableauResource.MOVES)
public class MoveFromWasteToTableauResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/WasteToTableau";
    @Autowired
    private MoveFromWasteToTableauController moveFromWasteToTableauController;

    @PostMapping(value = MoveFromWasteToTableauResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveWasteToTableauDto moveDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.moveFromWasteToTableauController.move(moveDto, login);
    }
}
