package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromWasteToFoundationController;
import es.upm.miw.klondike.dtos.MoveWasteToFoundationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(MoveFromWasteToFoundationResource.MOVES)
public class MoveFromWasteToFoundationResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/WasteToFoundation";
    @Autowired
    private MoveFromWasteToFoundationController moveFromWasteToFoundationController;

    @PostMapping(value = MoveFromWasteToFoundationResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(@Valid @RequestBody MoveWasteToFoundationDto moveDto, HttpSession session) {
        this.moveFromWasteToFoundationController.move(moveDto, session);
    }
}
