package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.exceptions.NotFoundException;
import es.upm.miw.klondike.models.Game;

import javax.servlet.http.HttpSession;

public abstract class GameController {

    private static Game game = new Game();

    protected Game getGame(HttpSession session){
        /*
        Game game = (Game)session.getAttribute("game");
        if(game == null) {
            //////////throw new NotFoundException("Game");
            session.setAttribute("game",new Game());
            game = (Game)session.getAttribute("game");
        }
        */
        if(game == null)
            throw new NotFoundException("Game");
        return game;
    }

    protected void setGame(HttpSession session, Game game){
        GameController.game = game;
    }
}
