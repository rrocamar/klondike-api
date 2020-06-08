package es.upm.miw.klondike.business_controllers;

import es.upm.miw.klondike.dtos.GameDto;
import es.upm.miw.klondike.models.Game;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class NewGameController extends GameController{

    public void createNewGame(String login){
        super.createNewGame(login, new Game());
    }
}
