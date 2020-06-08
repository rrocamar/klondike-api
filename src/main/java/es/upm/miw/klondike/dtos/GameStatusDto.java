package es.upm.miw.klondike.dtos;

public class GameStatusDto {

    private boolean gameInPlay;

    private boolean playerWin;

    public GameStatusDto() {
        this.gameInPlay = false;
        this.playerWin = false;
    }

    public boolean isGameInPlay() {
        return gameInPlay;
    }

    public void setGameInPlay(boolean gameInPlay) {
        this.gameInPlay = gameInPlay;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    @Override
    public String toString() {
        return "GameStatusDto{" +
                "gameInPlay=" + gameInPlay +
                ", playerWin=" + playerWin +
                '}';
    }
}
