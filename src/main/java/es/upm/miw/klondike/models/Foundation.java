package es.upm.miw.klondike.models;

public class Foundation extends Pile{

    public Foundation(){
        super();
    }

    @Override
    public boolean isValidMove(Card card) {
        if(!this.isEmpty()){
            Card cardOnTop = viewCardOnTop();
            return cardOnTop.getSuite() == card.getSuite() && card.getRank().ordinal() == cardOnTop.getRank().ordinal()+1;
        }
        return card.getRank() == Rank.ACE;
    }
}
