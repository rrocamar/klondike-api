package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromTableauToTableauController;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.dtos.MoveTableauToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(MoveFromTableauToTableauResource.MOVES)
public class MoveFromTableauToTableauResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/TableauToTableau";

    @Autowired
    private MoveFromTableauToTableauController moveFromTableauToTableauController;

    @PostMapping(value = MoveFromTableauToTableauResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveTableauToTableauDto moveDto, HttpSession session) {
        this.moveFromTableauToTableauController.move(moveDto, session);
    }
}
