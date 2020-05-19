package es.upm.miw.klondike.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class MoveWasteToTableauDto {

    @NotNull(message = "Please provide a tableau destination")
    @Min(0)
    @Max(6)
    private Integer tableauDestination;

    public MoveWasteToTableauDto(){
    }

    public int getTableauDestination() {
        return tableauDestination;
    }

    public void setTableauDestination(int tableauDestination) {
        this.tableauDestination = tableauDestination;
    }
}
