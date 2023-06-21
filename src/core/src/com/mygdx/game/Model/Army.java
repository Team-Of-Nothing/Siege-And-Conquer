package com.mygdx.game.Model;

import java.util.ArrayList;

import com.mygdx.game.Mercenary;

public class Army {

    ArrayList<Mercenary> army;


    public Army() {
        int size = 5;
        this.army = new ArrayList<Mercenary>(size);
    }


    public ArrayList<Mercenary> getArmy() {
        return this.army;
    }

    public void addMercenary(Mercenary mercenary, int index) {
        this.army.add(mercenary);
        
    }

    public void removeMercenary(int index) {
        this.army.remove(index);
    }

    public void clearArmy() {
        this.army.clear();
    }

}
