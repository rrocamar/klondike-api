package es.upm.miw.klondike.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveWasteToFoundationDto {

    @NotNull(message = "Please provide a foundation destination")
    @Min(0)
    @Max(3)
    private Integer foundationDestination;

    public MoveWasteToFoundationDto(){
    }

    public Integer getFoundationDestination() {
        return foundationDestination;
    }

    public void setFoundationDestination(Integer foundationDestination) {
        this.foundationDestination = foundationDestination;
    }
}
