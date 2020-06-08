package es.upm.miw.klondike.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameCaretaker {
    private ArrayList<GameMemento> states;
    private int actual;

    private static int NO_DIRECCTION = -1;
    private static int FORWARD = 1;
    private static int BACKWARD = 2;

    private int direction;
    public GameCaretaker(){
        this.states = new ArrayList<>();
        this.actual = -1;
    }

    public boolean isUndoable(){
        return this.actual > 0;
    }

    public boolean isRedoable(){
        return this.actual+1 < this.states.size();
    }

    public GameMemento getUndoMemento(){
        assert this.actual > 0 && this.actual<this.states.size();
        this.actual --;
        GameMemento gameMemento = this.states.get(actual);
        return gameMemento;
    }

    public GameMemento getRedoMemento(){
        assert this.actual > 0 && this.actual<this.states.size();
        this.actual ++;
        GameMemento gameMemento = this.states.get(actual);
        return gameMemento;
    }

    public void addMemento(GameMemento gameMemento){
        while(this.states.size()>this.actual+1)
            this.states.remove(this.states.size()-1);
        this.states.add(gameMemento);
        this.actual ++;
    }

}
