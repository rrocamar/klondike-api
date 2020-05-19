package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Waste;

public class WasteDto extends PileDto{

    public WasteDto() {
        super();
    }

    public WasteDto(Waste waste) {
        super(waste);
    }

    @Override
    public String toString() {
        return "WasteDto{" +
                "cards=" + getCards() +
                '}';
    }
}
