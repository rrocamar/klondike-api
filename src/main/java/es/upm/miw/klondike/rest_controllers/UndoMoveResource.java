package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromWasteToTableauController;
import es.upm.miw.klondike.business_controllers.UndoMoveController;
import es.upm.miw.klondike.dtos.MoveWasteToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(UndoMoveResource.MOVES)
public class UndoMoveResource {

    public static final String MOVES = "/moves";
    public static final String UNDO_MOVE ="/undo";
    @Autowired
    private UndoMoveController undoMoveController;

    @PostMapping(value = UndoMoveResource.UNDO_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void undo() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.undoMoveController.undo(login);
    }
}
