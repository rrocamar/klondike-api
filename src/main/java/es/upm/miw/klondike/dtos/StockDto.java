package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Stock;

public class StockDto extends PileDto{

    public StockDto() {
        super();
    }

    public StockDto(Stock stock) {
        super(stock);
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "cards=" + getCards() +
                '}';
    }
}
