package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Tableau;

public class TableauDto extends PileDto{

    public TableauDto() {
        super();
    }

    public TableauDto(Tableau tableau) {
        super(tableau);
    }

    @Override
    public String toString() {
        return "TableauDto{" +
                "cards=" + getCards() +
                '}';
    }
}
