package es.upm.miw.klondike.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveTableauToTableauDto {

    @NotNull(message = "Please provide a tableau destination")
    @Min(0)
    @Max(6)
    private Integer tableauDestination;

    @NotNull(message = "Please provide a tableau source")
    @Min(0)
    @Max(6)
    private Integer tableauSource;

    @NotNull(message = "Please provide the number of cards to move")
    @Min(1)
    @Max(13)
    private Integer numberOfCards;

    public MoveTableauToTableauDto(){
    }

    public Integer getTableauDestination() {
        return tableauDestination;
    }

    public void setTableauDestination(Integer tableauDestination) {
        this.tableauDestination = tableauDestination;
    }

    public Integer getTableauSource() {
        return tableauSource;
    }

    public void setTableauSource(Integer tableauSource) {
        this.tableauSource = tableauSource;
    }

    public Integer getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Integer numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
