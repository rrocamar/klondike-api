package es.upm.miw.klondike.business_controllers;

import org.springframework.stereotype.Controller;

@Controller
public class QuitGameController extends GameController{

    public void quitGame(String login) {
        super.removeGame(login);
    }
}
