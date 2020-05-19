package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.RecycleWasteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(RecycleWasteResource.MOVES)
public class RecycleWasteResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/RecycleWaste";
    @Autowired
    private RecycleWasteController recycleWasteController;

    @PostMapping(value = RecycleWasteResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(HttpSession session) {
        recycleWasteController.move(session);
    }
}
