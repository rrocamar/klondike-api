package es.upm.miw.klondike.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveTableauToFoundationDto {

    @NotNull(message = "Please provide a tableau source")
    @Min(0)
    @Max(6)
    private Integer tableauSource;

    @NotNull(message = "Please provide a foundation destination")
    @Min(0)
    @Max(3)
    private Integer foundationDestination;

    public MoveTableauToFoundationDto(){
    }

    public Integer getTableauSource() {
        return tableauSource;
    }

    public void setTableauSource(Integer tableauSource) {
        this.tableauSource = tableauSource;
    }

    public Integer getFoundationDestination() {
        return foundationDestination;
    }

    public void setFoundationDestination(Integer foundationDestination) {
        this.foundationDestination = foundationDestination;
    }
}
