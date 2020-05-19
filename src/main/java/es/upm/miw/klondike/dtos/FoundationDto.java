package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Foundation;

public class FoundationDto extends PileDto{

    public FoundationDto(){
        super();
    }

    public FoundationDto(Foundation foundation){
        super(foundation);
    }

    @Override
    public String toString() {
        return "FoundationDto{" +
                "cards=" + getCards() +
                '}';
    }
}
