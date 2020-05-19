package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShowGameController extends GameController{

    public GameDto showGame(HttpSession session) {
        Game game = getGame(session);
        return new GameDto(game);
    }

    public List<TableauDto> showTableaus(HttpSession session) {
        Game game = getGame(session);
        return new GameDto(game).getTableaus();
    }

    public List<FoundationDto> showFoundations(HttpSession session) {
        Game game = getGame(session);
        return new GameDto(game).getFoundations();
    }

    public StockDto showStock(HttpSession session) {
        Game game = getGame(session);
        return new GameDto(game).getStock();
    }

    public WasteDto showWaste(HttpSession session) {
        Game game = getGame(session);
        return new GameDto(game).getWaste();
    }
}
