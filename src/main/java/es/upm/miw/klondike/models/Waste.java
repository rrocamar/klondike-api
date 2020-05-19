package es.upm.miw.klondike.models;

public class Waste extends Pile {

    public Waste(){
        super();
    }

    @Override
    public boolean isValidMove(Card card) {
        return true;
    }
}
