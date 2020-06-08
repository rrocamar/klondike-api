package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.*;
import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShowGameController extends GameController{

    public GameDto showGame(String login) {
        Game game = getGame(login);
        return new GameDto(game);
    }

    public List<TableauDto> showTableaus(String login) {
        Game game = getGame(login);
        return new GameDto(game).getTableaus();
    }

    public List<FoundationDto> showFoundations(String login) {
        Game game = getGame(login);
        return new GameDto(game).getFoundations();
    }

    public StockDto showStock(String login) {
        Game game = getGame(login);
        return new GameDto(game).getStock();
    }

    public WasteDto showWaste(String login) {
        Game game = getGame(login);
        return new GameDto(game).getWaste();
    }

    public GameStatusDto showGameStatus(String login) {
        GameStatusDto gameStatusDto = new GameStatusDto();
        Game game;
        try{
            game = getGame(login);
            gameStatusDto.setGameInPlay(true);
            gameStatusDto.setPlayerWin(game.isPlayerWin());
        }catch(NotFoundException ex){
        }
        return gameStatusDto;
    }
}
