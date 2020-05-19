package es.upm.miw.klondike.models;

import java.util.Arrays;

public class Game {

    private Foundation[] foundations;
    private Tableau[] tableaus;
    private Stock stock;
    private Waste waste;

    private static int INITIALS_CARDS_ON_STOCK = 24;

    public static int NUMBER_OF_FOUNDATIONS = 4;
    public static int NUMBER_OF_TABLEAUS = 7;

    public Game() {
        Deck deck = new Deck();
        deck.shuffle();
        this.stock = new Stock(deck.getCards(Game.INITIALS_CARDS_ON_STOCK));
        this.waste = new Waste();
        this.tableaus = new Tableau[Game.NUMBER_OF_TABLEAUS];
        for(int i=0;i<tableaus.length;i++){
            int numberOfCardsForTableau = i + 1;
            this.tableaus[i] = new Tableau(deck.getCards(numberOfCardsForTableau));
        }
        for(int i=0;i<tableaus.length;i++) {
            Card card = this.tableaus[i].getCardOnTop();
            card.setUpturned(true);
            this.tableaus[i].putCardOnTop(card);
        }
        this.foundations = new Foundation[Game.NUMBER_OF_FOUNDATIONS];
        for(int i=0;i<foundations.length;i++)
            this.foundations[i] = new Foundation();
    }

    public Stock getStock(){
        return this.stock;
    }

    public Waste getWaste(){
        return this.waste;
    }

    public Foundation getFoundation(int number){
        assert number >=0 && number < Game.NUMBER_OF_FOUNDATIONS;
        return this.foundations[number];
    }

    public Tableau getTableau(int number){
        assert number >=0 && number < Game.NUMBER_OF_TABLEAUS;
        return this.tableaus[number];
    }

    @Override
    public String toString() {
        return "Game{" +
                "foundations=" + Arrays.toString(foundations) +
                ", tableaus=" + Arrays.toString(tableaus) +
                ", stock=" + stock +
                ", waste=" + waste +
                '}';
    }
}
