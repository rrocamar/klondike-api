package es.upm.miw.klondike.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveFoundationToTableauDto {

    @NotNull(message = "Please provide a tableau destination")
    @Min(0)
    @Max(6)
    private Integer tableauDestination;

    @NotNull(message = "Please provide a foundation source")
    @Min(0)
    @Max(3)
    private Integer foundationSource;

    public MoveFoundationToTableauDto(){
    }

    public Integer getTableauDestination() {
        return tableauDestination;
    }

    public void setTableauDestination(Integer tableauDestination) {
        this.tableauDestination = tableauDestination;
    }

    public Integer getFoundationSource() {
        return foundationSource;
    }

    public void setFoundationSource(Integer foundationSource) {
        this.foundationSource = foundationSource;
    }
}
