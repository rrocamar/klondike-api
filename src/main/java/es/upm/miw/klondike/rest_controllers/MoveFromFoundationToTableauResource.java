package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromFoundationToTableauController;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(MoveFromFoundationToTableauResource.MOVES)
public class MoveFromFoundationToTableauResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/FoundationToTableau";

    @Autowired
    private MoveFromFoundationToTableauController moveFromFoundationToTableauController;

    @PostMapping(value = MoveFromFoundationToTableauResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveFoundationToTableauDto moveDto, HttpSession session) {
        this.moveFromFoundationToTableauController.move(moveDto, session);
    }
}
