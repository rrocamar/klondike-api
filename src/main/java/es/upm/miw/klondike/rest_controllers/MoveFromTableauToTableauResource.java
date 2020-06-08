package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromTableauToTableauController;
import es.upm.miw.klondike.dtos.MoveFoundationToTableauDto;
import es.upm.miw.klondike.dtos.MoveTableauToTableauDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(MoveFromTableauToTableauResource.MOVES)
public class MoveFromTableauToTableauResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/TableauToTableau";

    @Autowired
    private MoveFromTableauToTableauController moveFromTableauToTableauController;

    @PostMapping(value = MoveFromTableauToTableauResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveTableauToTableauDto moveDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.moveFromTableauToTableauController.move(moveDto, login);
    }
}
