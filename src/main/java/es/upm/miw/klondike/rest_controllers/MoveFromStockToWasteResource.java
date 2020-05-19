package es.upm.miw.klondike.rest_controllers;

import es.upm.miw.klondike.business_controllers.MoveFromStockToWasteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(MoveFromStockToWasteResource.MOVES)
public class MoveFromStockToWasteResource {

    public static final String MOVES = "/moves";
    public static final String TYPE_MOVE ="/StockToWaste";

    @Autowired
    private MoveFromStockToWasteController moveFromStockToWasteController;

    @PostMapping(value = MoveFromStockToWasteResource.TYPE_MOVE)
    @ResponseStatus(value = HttpStatus.OK)
    public void move(HttpSession session) {
        this.moveFromStockToWasteController.move(session);
    }
}
