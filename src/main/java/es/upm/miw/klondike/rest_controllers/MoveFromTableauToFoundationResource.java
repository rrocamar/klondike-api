package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromTableauToFoundationController;
import es.upm.miw.klondike.dtos.MoveTableauToFoundationDto;
import es.upm.miw.klondike.dtos.MoveWasteToFoundationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@PreAuthorize("hasRole('PLAYER')")
@RestController
@RequestMapping(MoveFromTableauToFoundationResource.MOVES)
public class MoveFromTableauToFoundationResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/TableauToFoundation";

    @Autowired
    private MoveFromTableauToFoundationController moveFromTableauToFoundationController;

    @PostMapping(value = MoveFromTableauToFoundationResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveTableauToFoundationDto moveDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        this.moveFromTableauToFoundationController.move(moveDto, login);
    }
}
