package es.upm.miw.klondike.models;

import java.util.ArrayList;
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
        this.waste = new Waste(new ArrayList<Card>());
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
            this.foundations[i] = new Foundation(new ArrayList<Card>());
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

    public boolean isPlayerWin() {
        for(int i=0;i<Game.NUMBER_OF_TABLEAUS;i++)
            if (!this.getTableau(i).isEmpty())
                return false;
        return this.getStock().isEmpty() && this.getWaste().isEmpty();
    }

    public GameMemento createMemento(){
        GameMemento.Builder builder = GameMemento.builder();
        builder.stock((Stock)this.getStock().clone()).waste((Waste)this.getWaste().clone());
        for (int i=0; i<Game.NUMBER_OF_TABLEAUS;i++)
            builder.addTableau((Tableau)this.getTableau(i).clone());
        for (int i=0; i<Game.NUMBER_OF_FOUNDATIONS;i++)
            builder.addFoundation((Foundation)this.getFoundation(i).clone());
        return builder.build();
    }

    public void restoreMemento(GameMemento gameMemento){
        this.stock = (Stock)gameMemento.getStock().clone();
        this.waste = (Waste)gameMemento.getWaste().clone();
        for (int i=0; i<Game.NUMBER_OF_TABLEAUS;i++)
            this.tableaus[i] = (Tableau)gameMemento.getTableaus().get(i).clone();
        for (int i=0; i<Game.NUMBER_OF_FOUNDATIONS;i++)
            this.foundations[i] = (Foundation) gameMemento.getFoundations().get(i).clone();
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
