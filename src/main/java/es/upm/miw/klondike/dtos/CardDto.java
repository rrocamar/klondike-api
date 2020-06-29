package es.upm.miw.klondike.dtos;

import es.upm.miw.klondike.models.Card;

import java.util.Objects;

public class CardDto {

    private int rank;
    private String suite;
    private boolean upturned;

    public CardDto(){
    }

    public CardDto(Card card){
        this.upturned = card.isUpturned();
        if(upturned){
            this.rank = card.getRank().ordinal()+1;
            this.suite = card.getSuite().name();
        }else{
            this.rank = card.getRank().ordinal()+1;
            this.suite = card.getSuite().name();
        }
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
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
        CardDto cardDto = (CardDto) o;
        return rank == cardDto.rank &&
                upturned == cardDto.upturned &&
                Objects.equals(suite, cardDto.suite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suite, upturned);
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "rank=" + rank +
                ", suite='" + suite + '\'' +
                ", upturned=" + upturned +
                '}';
    }
}
