package es.upm.miw.klondike.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class GameMemento implements Serializable {

    private List<Foundation> foundations;
    private List<Tableau> tableaus;
    private Stock stock;
    private Waste waste;

    public GameMemento() {
        this.foundations = new ArrayList<>();
        this.tableaus = new ArrayList<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Foundation> getFoundations() {
        return foundations;
    }

    public List<Tableau> getTableaus() {
        return tableaus;
    }

    public Stock getStock() {
        return stock;
    }

    public Waste getWaste() {
        return waste;
    }

    void setWaste(Waste waste){
        this.waste = waste;
    }

    void setStock(Stock stock){
        this.stock = stock;
    }

    public static class Builder {
        private GameMemento gameMemento;

        private Builder() {
            this.gameMemento = new GameMemento();
        }

        public Builder addTableau(Tableau tableau){
            this.gameMemento.getTableaus().add((Tableau)tableau.clone());
            return this;
        }

        public Builder addFoundation(Foundation foundation){
            this.gameMemento.getFoundations().add((Foundation) foundation.clone());
            return this;
        }

        public Builder waste(Waste waste){
            this.gameMemento.setWaste((Waste)waste.clone());
            return this;
        }

        public Builder stock(Stock stock){
            this.gameMemento.setStock((Stock)stock.clone());
            return this;
        }

        public GameMemento build(){
            return this.gameMemento;
        }

    }
}