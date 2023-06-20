package com.mygdx.game;

import java.util.ArrayList;

public class Army {

    ArrayList<Mercenary> army = new ArrayList<Mercenary>();


    public Army() {
        int size = 5;
        this.army = new ArrayList<Mercenary>();
        this.army.ensureCapacity(size);
    }


    public ArrayList<Mercenary> getArmy() {
        return this.army;
    }

    public void addMercenary(Mercenary mercenary, int index) {
        this.army.set(index, mercenary);
    }

    public void removeMercenary(int index) {
        this.army.remove(index);
    }




    public void clearArmy() {
        this.army.clear();
    }

}








