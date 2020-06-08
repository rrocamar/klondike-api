package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.RedoMoveController;
import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(RedoMoveResource.MOVES)
public class RedoMoveResource {

    public static final String MOVES = "/moves";
    public static final String REDO_MOVE ="/redo";
    @Autowired
    private RedoMoveController redoMoveController;

    @PostMapping(value = RedoMoveResource.REDO_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void redo() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.redoMoveController.redo(login);
    }
}
