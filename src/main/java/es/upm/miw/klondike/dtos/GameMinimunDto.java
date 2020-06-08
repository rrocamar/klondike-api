package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.SavedGame;

import javax.validation.constraints.NotNull;

public class GameMinimunDto {

    private Long id;
    @NotNull(message = "Please provide a name for the game")
    private String name;

    public GameMinimunDto(){
    }

    public GameMinimunDto(SavedGame savedGame){
        this.id = savedGame.getId();
        this.name = savedGame.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
