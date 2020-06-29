package es.upm.miw.klondike.models;

import java.io.Serializable;
import java.util.Objects;


public class Card implements Cloneable, Serializable {

    private static final long serialVersionUID = -2393155049458730913l;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return upturned == card.upturned &&
                rank == card.rank &&
                suite == card.suite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suite, upturned);
    }

    @Override
    public Object clone() {
        Card copy = new Card(this.getRank(),this.getSuite());
        copy.setUpturned(this.isUpturned());
        return copy;
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
