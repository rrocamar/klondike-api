package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GameDto {

    private StockDto stock;

    private WasteDto waste;

    private List<FoundationDto> foundations;

    private List<TableauDto> tableaus;

    public GameDto(){

    }

    public GameDto(Game game){
        this.stock = new StockDto(game.getStock());
        this.waste = new WasteDto(game.getWaste());
        this.foundations = new ArrayList<>();
        this.tableaus = new ArrayList<>();
        for(int i=0;i<Game.NUMBER_OF_TABLEAUS;i++)
            this.tableaus.add(new TableauDto(game.getTableau(i)));
        for(int i=0;i<Game.NUMBER_OF_FOUNDATIONS;i++)
            this.foundations.add(new FoundationDto(game.getFoundation(i)));
    }

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public WasteDto getWaste() {
        return waste;
    }

    public void setWaste(WasteDto waste) {
        this.waste = waste;
    }

    public List<FoundationDto> getFoundations() {
        return foundations;
    }

    public void setFoundations(List<FoundationDto> foundations) {
        this.foundations = foundations;
    }

    public List<TableauDto> getTableaus() {
        return tableaus;
    }

    public void setTableaus(List<TableauDto> tableaus) {
        this.tableaus = tableaus;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "stockDto=" + stock +
                ", wasteDto=" + waste +
                ", tableauDtoList=" + tableaus +
                ", foundationDtoList=" + foundations +
                '}';
    }
}
