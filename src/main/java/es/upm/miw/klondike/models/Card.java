package es.upm.miw.klondike.models;

public class Card {

    private Rank rank;

    private Suite suite;

    private boolean upturned;

    public Card(Rank rank, Suite suite){
        this.rank = rank;
        this.suite = suite;
        this.upturned = false;
    }

    public Rank getRank() {
        return rank;
    }

    public Suite getSuite() {
        return suite;
    }

    public Color getColor() {
        if(suite==Suite.CLUBS||suite==Suite.SPADES)
            return Color.BLACK;
        return Color.RED;
    }

    public boolean isUpturned() {
        return upturned;
    }

    public void setUpturned(boolean upturned) {
        this.upturned = upturned;
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suite=" + suite +
                ", upturned=" + upturned +
                '}';
    }
}
